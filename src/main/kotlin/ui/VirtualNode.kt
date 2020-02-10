package ui

data class VirtualNode<Props: Any>(
    override val type: VirtualNodeType<Props>,
    override val props: Props,
    val children: List<VirtualNode<*>>
): PropsOwner<Props>

interface VirtualNodeType<Props: Any>

object NoProps

object NoPropsType : VirtualNodeType<NoProps>