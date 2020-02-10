package ui.nodes

import ui.RenderContext
import ui.VirtualNode
import ui.VirtualNodeType

data class ButtonProps(val label: String, val onClick: () -> Unit) {
    override fun toString(): String {
        return "ButtonProps(label='$label')"
    }
}

object ButtonType : VirtualNodeType<ButtonProps> {
    override fun toString(): String {
        return "Button"
    }
}

fun RenderContext.button(label: String, onClick: () -> Unit) {
    add(VirtualNode(ButtonType, ButtonProps(label, onClick), emptyList()))
}