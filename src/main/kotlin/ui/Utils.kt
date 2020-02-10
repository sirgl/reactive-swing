package ui

inline fun guarded(before: () -> Unit, block: () -> Unit, after: () -> Unit) {
    before()
    try {
        block()
    } finally {
        after()
    }
}

fun treeWithIndents(node: VirtualNode<*>): String {
    val sb = StringBuilder()
    treeWithIndents(sb, node, 0)
    return sb.toString()
}

private fun treeWithIndents(sb: StringBuilder, node: VirtualNode<*>, indentLevel: Int) {
    with(sb) {
        repeat(indentLevel) {
            append("  ")
        }
        append(node.type)
        append(" props=")
        appendln(node.props)
    }
    for (child in node.children) {
        treeWithIndents(sb, child, indentLevel + 1)
    }
}