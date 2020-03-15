package ui.nodes

import ui.RenderContext
import ui.VirtualNode
import ui.VirtualNodeType
import kotlin.reflect.KMutableProperty0

data class InputFieldProps(
    val binding: KMutableProperty0<String>
) {
    override fun toString(): String {
        return "InputFieldProps(binding=${binding.get()})"
    }
}

object InputFieldType : VirtualNodeType<InputFieldProps> {
    override fun toString(): String = "InputField"
}

fun RenderContext.inputField(binding: KMutableProperty0<String>) {
    add(VirtualNode(InputFieldType, InputFieldProps(binding), emptyList()))
}