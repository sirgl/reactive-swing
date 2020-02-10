package ui

// TODO maybe add here UI kit?
abstract class Component<Props: Any>(val props: Props, val kit: UiKit) {
    abstract fun RenderContext.render()
    fun shouldUpdate(newProps: Props) : Boolean {
        return props == newProps
    }
}

interface RenderContext {
    fun add(node: VirtualNode<*>)
}