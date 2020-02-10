package ui.cells

import java.util.*

object DepGraph {
    private val stack = ArrayDeque<DependencyNode>()

    fun pushFrame(node: DependencyNode) {
        stack.push(node)
    }

    fun popFrame() : DependencyNode? {
        return stack.pollLast()
    }

    fun peekFrame() : DependencyNode? {
        return stack.peekLast()
    }
}


class DependencyNode(
    var isValid: Boolean,
    val dependencies: MutableSet<DependencyNode>
) {
    fun invalidateDependencies() {
        val stack = ArrayDeque<DependencyNode>()
        val visited = HashSet<DependencyNode>()
        dependencies.forEach {
            stack.push(it)
        }
        while (stack.isNotEmpty()) {
            val node = stack.pop()
            visited.add(node)
            node.isValid = false
            for (dependant in node.dependencies) {
                if (!dependant.isValid) continue
                stack.push(dependant)
            }
        }
    }
}

