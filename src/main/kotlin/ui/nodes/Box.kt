package ui.nodes

import ui.NoProps
import ui.RenderContext
import ui.VirtualNode
import ui.VirtualNodeType

object BoxType : VirtualNodeType<NoProps> {
    override fun toString(): String {
        return "Box"
    }
}

// TODO probably builder should not use RenderContext
fun RenderContext.box(isVertical: Boolean = true, builder: (RenderContext.() -> Unit)? = null) {
    val nodes: List<VirtualNode<*>> = when {
        builder != null -> {
            val accumulator = mutableListOf<VirtualNode<*>>()
            val boxContext = object : RenderContext {
                override fun add(node: VirtualNode<*>) {
                    accumulator.add(node)
                }
            }
            builder(boxContext)
            accumulator
        }
        else -> emptyList()
    }
    add(VirtualNode(BoxType, NoProps, nodes))
}