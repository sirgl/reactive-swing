package ui.swing

import ui.AbstractWidget
import ui.NoProps
import ui.VirtualNodeType
import ui.Widget
import ui.nodes.BoxType
import javax.swing.BoxLayout
import javax.swing.JPanel

class BoxWidget(
    children: MutableList<Widget<*>>
) : AbstractWidget<NoProps>(NoProps, children), SwingComponentOwner {
    override val component = JPanel().also {
        it.layout = BoxLayout(it, BoxLayout.Y_AXIS)
    }

    init {
        for (child in children) {
            this.component.add((child as SwingComponentOwner).component)
        }
        this.component.revalidate()
        this.component.repaint()
    }

    override val type: VirtualNodeType<NoProps>
        get() = BoxType

    override fun updateFromProps(newProps: NoProps) {

    }

    override fun addChild(index: Int, child: Widget<*>) {
        super.addChild(index, child)
        child as SwingComponentOwner
        component.add(child.component, index)
        component.revalidate()
    }

    override fun removeChild(index: Int): Widget<*> {
        val widget = super.removeChild(index)
        component.remove(index)
        component.revalidate()
        return widget
    }
}