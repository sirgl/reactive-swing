package ui.cells

// TODO probably it should be called explicitly "input"
interface MutableCell<T> : Cell<T> {
    override var value: T
}

class MutableCellImpl<T>(initial: T) : MutableCell<T> {
    private var _value: T = initial
    private val node = DependencyNode(true, hashSetOf())

    override var value: T
        get() = _value
        set(value) {
            DepGraph.peekFrame()
                ?.dependencies
                ?.add(node)
            _value = value
            node.invalidateDependencies()
        }

}

fun <T> input(initial: T) : MutableCell<T> {
    return MutableCellImpl(initial)
}