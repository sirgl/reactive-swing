package ui

interface PropsOwner<Props: Any> {
    val props: Props
    val type: VirtualNodeType<Props>
}

fun <Props: Any>VirtualNodeType<Props>.props(propsOwner: PropsOwner<*>) : Props {
    if (propsOwner.type != this) {
        throw IllegalArgumentException("owner: $propsOwner, owner type: ${propsOwner.type}, current type: $this")
    }
    @Suppress("UNCHECKED_CAST")
    return propsOwner.props as Props
}