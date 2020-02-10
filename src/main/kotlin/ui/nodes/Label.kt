package ui.nodes

import ui.RenderContext
import ui.VirtualNode
import ui.VirtualNodeType

object LabelType : VirtualNodeType<String> {
    override fun toString(): String {
        return "Label"
    }
}

fun RenderContext.label(text: String) {
    add(VirtualNode(LabelType, text, emptyList()))
}