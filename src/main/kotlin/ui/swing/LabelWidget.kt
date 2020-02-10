package ui.swing


import ui.LeafWidget
import ui.VirtualNodeType
import ui.Widget
import ui.nodes.LabelType
import javax.swing.JLabel

class LabelWidget(
    override val props: String
) : LeafWidget<String>(props), SwingComponentOwner {
    override val component = JLabel()

    override fun updateFromProps(newProps: String) {
        component.text = newProps
    }

    override val children: List<Widget<*>>
        get() = emptyList()

    override val type: VirtualNodeType<String>
        get() = LabelType

    override fun toString(): String {
        return "LabelWidget(props='$props')"
    }
}
