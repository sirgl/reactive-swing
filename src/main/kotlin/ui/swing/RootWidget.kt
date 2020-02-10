package ui.swing

import ui.NoProps
import ui.NoPropsType
import ui.VirtualNodeType
import ui.Widget

class RootWidget: Widget<NoProps> {
    private val _children = mutableListOf<Widget<*>>()

    override fun updateFromProps(newProps: NoProps) {
        throw UnsupportedOperationException()
    }

    override val children: List<Widget<*>>
        get() = _children

    override fun addChild(index: Int, child: Widget<*>) {
        _children.add(index, child)
    }

    override fun removeChild(index: Int): Widget<*> {
        return _children.removeAt(index)
    }

    override fun shouldUpdate(newProps: NoProps): Boolean {
        throw UnsupportedOperationException()
    }

    override val props: NoProps
        get() = NoProps
    override val type: VirtualNodeType<NoProps>
        get() = NoPropsType
}