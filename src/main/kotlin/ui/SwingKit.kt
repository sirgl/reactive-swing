package ui

import ui.nodes.BoxType
import ui.nodes.ButtonType
import ui.nodes.LabelType
import ui.swing.*
import javax.swing.JComponent
import kotlin.system.measureTimeMillis


class SwingKit(private val logger: Logger) : UiKit {
    private lateinit var root: Widget<*>
    lateinit var treeBuilder: () -> VirtualNode<*>
    var reconciliationInProgress = false

    override fun createNativeWidget(node: VirtualNode<*>): Widget<*> {
        val widget: Widget<out Any> = when (val type = node.type) {
            is ButtonType -> ButtonWidget(type.props(node))
            is LabelType -> LabelWidget(type.props(node))
            is BoxType -> BoxWidget(node.children.map { createNativeWidget(it) }.toMutableList())
            else -> throw UnsupportedOperationException("$type node is not native for swing")
        }
        widget.tryUpdateProperties(node)
        return widget
    }

    override fun reconcile() {
        guarded(
            before = { reconciliationInProgress = true },
            block = {
                val newRoot = treeBuilder()
                logger.log(LogFeature.NewTree) { treeWithIndents(newRoot) }
                val collecting = CollectingReconciler()
                val reconciler = if (logger.isEnabled(LogFeature.ReconciliationOps)) {
                    val reconcilers = listOf(
                        collecting,
                        Reconciler(converter = ::createNativeWidget)
                    )
                    CompoundReconciler(reconcilers)
                } else {
                    Reconciler(converter = ::createNativeWidget)
                }
                val time = measureTimeMillis {
                    reconcile(newRoot, root, reconciler)
                }
                logger.log(LogFeature.ReconciliationStats) {
                    "Reconciliation finished in $time ms"
                }
                logger.log(LogFeature.ReconciliationOps) {
                    "Reconciliation ops: \n" +
                            collecting.ops.joinToString("\n") { it.toString() } +
                            "\n------"
                }
            },
            after = { reconciliationInProgress = false }
        )

    }

    override fun isReconciliationInProgress(): Boolean {
        return reconciliationInProgress
    }

    /**
     * [component] can't be used after mount
     */
    fun mount(component: Component<*>): JComponent {
        val rootNode = renderComponent(component)
        logger.log(LogFeature.NewTree) { treeWithIndents(rootNode) }
        val widget = createNativeWidget(rootNode)
        widget as SwingComponentOwner
        val rootWidget = RootWidget()
        rootWidget.addChild(0, widget)
        // TODO set real root
        this.root = widget
        this.treeBuilder = { renderComponent(component) }
        return widget.component
    }
}

private fun renderComponent(component: Component<*>): VirtualNode<out Any> {
    var root: VirtualNode<*>? = null

    val context: RenderContext = object : RenderContext {
        override fun add(node: VirtualNode<*>) {
            if (root != null) throw IllegalStateException("Root may be the only node!")
            root = node
        }
    }
    with(context) {
        with(component) {
            render()
        }
    }
    return root ?: error("Root must be set up")
}
