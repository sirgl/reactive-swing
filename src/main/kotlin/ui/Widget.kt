package ui

/**
 * Manages directly native component (e. g. JTextField), stores it inside
 */
interface Widget<Props: Any> : PropsOwner<Props> { // TODO not really widget! some kind of wrapper really
    fun updateFromProps(newProps: Props)

    val children: List<Widget<*>>

    // TODO maybe bulk operations to add and remove in a single operation to avoid list operations and memory traffic
    fun addChild(index: Int, child: Widget<*>)

    fun removeChild(index: Int): Widget<*>

    fun shouldUpdate(newProps: Props): Boolean
}

abstract class PropsHoldingWidget<Props: Any>(props: Props) : Widget<Props> {
    internal var _props = props
    override val props: Props
        get() = _props

    override fun shouldUpdate(newProps: Props): Boolean {
        return _props != newProps
    }

    override fun updateFromProps(newProps: Props) {
        _props = newProps
    }
}

abstract class AbstractWidget<Props: Any>(
    props: Props,
    children: MutableList<Widget<*>>
) : PropsHoldingWidget<Props>(props) {
    private val _children: MutableList<Widget<*>> = children


    override val children: List<Widget<*>>
        get() = _children

    override fun addChild(index: Int, child: Widget<*>) {
        _children.add(index, child)
    }

    override fun removeChild(index: Int): Widget<*> {
        return _children.removeAt(index)
    }
}

abstract class LeafWidget<Props: Any>(props: Props) : PropsHoldingWidget<Props>(props) {
    override val children: List<Widget<*>>
        get() = emptyList()

    override fun addChild(index: Int, child: Widget<*>): Unit = throw UnsupportedOperationException()

    override fun removeChild(index: Int): Widget<*> = throw UnsupportedOperationException()
}

fun <T : Any> Widget<T>.tryUpdateProperties(propsOwner: PropsOwner<*>): Boolean {
    if (this.type != propsOwner.type) {
        return false
    }
    this as PropsHoldingWidget<T>
    @Suppress("UNCHECKED_CAST")
    val props = propsOwner.props as T
    updateFromProps(props)
    this._props = props
    return true
}

fun <T : Any> Widget<T>.shouldUpdateFrom(propsOwner: PropsOwner<*>): Boolean {
    if (this.type != propsOwner.type) {
        throw IllegalArgumentException()
    }
    @Suppress("UNCHECKED_CAST")
    return shouldUpdate(propsOwner.props as T)
}