package ui.cells

interface Cell<T> {
    val value: T
}

operator fun <T> Cell<T>.invoke(): T = value

fun <T>cell(f: () -> T) : Cell<T> {
    return CellImpl(DependencyNode(true, hashSetOf()), f)
}

open class CellImpl<T>(
    private val node: DependencyNode,
    val f: () -> T
) : Cell<T> {
    private var cached: T? = null

    override val value: T
        get() {
            DepGraph.peekFrame()
                ?.dependencies
                ?.add(node)
            val cachedValue = cached
            // Cached value == null only first time
            return if (cachedValue != null && node.isValid) {
                cachedValue
            } else {
                DepGraph.pushFrame(node)
                val value = try {
                    f()
                } finally {
                    DepGraph.popFrame()
                }
                CALCULATION_COUNT += 1
                cached = value
                value
            }
        }


    companion object {
        var CALCULATION_COUNT = 0
        private set
    }
}