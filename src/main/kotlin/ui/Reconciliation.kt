package ui

import java.lang.Integer.min

fun reconcile(newRoot: VirtualNode<*>, oldRoot: Widget<*>, visitor: ReconciliationVisitor) {
    reconcileChildren(oldRoot, newRoot, visitor)
}

fun reconcileNonRoot(newNode: VirtualNode<*>, oldNode: Widget<*>, visitor: ReconciliationVisitor, parent: Widget<*>) {
    if (newNode.type != oldNode.type) {
        val indexInParent = parent.children.indexOf(oldNode)
        visitor.deleteNode(oldNode, parent)
        visitor.addNode(parent, newNode, indexInParent)
    } else {
        if (oldNode.shouldUpdateFrom(newNode)) {
            visitor.changeProps(oldNode, newNode)
        }

        reconcileChildren(oldNode, newNode, visitor)
    }
}

private fun reconcileChildren(oldNode: Widget<*>, newNode: VirtualNode<*>, visitor: ReconciliationVisitor) {
    var index = 0
    val oldChildren = oldNode.children
    val newChildren = newNode.children
    val oldSize = oldChildren.size
    val newSize = newChildren.size
    val min = min(oldSize, newSize)
    while (index < min) { // Size may not change in this loop
        val oldChild = oldChildren[index]
        val newChild = newChildren[index]
        reconcileNonRoot(newChild, oldChild, visitor, oldNode)
        index++
    }
    if (oldSize > min) {
        for (i in min until oldSize) {
            val oldChild = oldChildren[index]
            visitor.deleteNode(oldChild, oldNode)
        }
    } else if (newSize > min) {
        for (i in min until newSize) {
            val newChild = newChildren[index]
            visitor.addNode(oldNode, newChild, i)
        }
    }
}

// TODO probably unnecessary
interface ReconciliationVisitor {
    fun addNode(parent: Widget<*>, node: VirtualNode<*>, indexInParent: Int)
    fun deleteNode(node: Widget<*>, parent: Widget<*>)
    fun changeProps(widget: Widget<*>, takePropsFrom: PropsOwner<*>)
}

class Reconciler(val converter: (VirtualNode<*>) -> Widget<*>) : ReconciliationVisitor {
    override fun addNode(parent: Widget<*>, node: VirtualNode<*>, indexInParent: Int) {
        parent.addChild(indexInParent, converter(node))
    }

    override fun deleteNode(node: Widget<*>, parent: Widget<*>) {
        parent.removeChild(parent.children.indexOf(node))
    }

    override fun changeProps(widget: Widget<*>, takePropsFrom: PropsOwner<*>) {
        if (!widget.tryUpdateProperties(takePropsFrom)) throw IllegalArgumentException()
    }
}

class CollectingReconciler : ReconciliationVisitor {
    val ops = mutableListOf<ReconcileOp>()

    override fun addNode(parent: Widget<*>, node: VirtualNode<*>, indexInParent: Int) {
     ops.add(ReconcileOp.AddNode(parent, node, indexInParent))
    }

    override fun deleteNode(node: Widget<*>, parent: Widget<*>) {
        ops.add(ReconcileOp.DeleteNode(node, parent))
    }

    override fun changeProps(widget: Widget<*>, takePropsFrom: PropsOwner<*>) {
        ops.add(ReconcileOp.ChangeProps(widget, takePropsFrom))
    }
}

sealed class ReconcileOp {
    data class AddNode(val parent: Widget<*>, val node: VirtualNode<*>, val indexInParent: Int) : ReconcileOp()
    data class DeleteNode(val node: Widget<*>, val parent: Widget<*>) : ReconcileOp()
    data class ChangeProps(val node: Widget<*>, val takePropsFrom: PropsOwner<*>) : ReconcileOp()
}

class CompoundReconciler(private val reconcilers: List<ReconciliationVisitor>) : ReconciliationVisitor {
    override fun addNode(parent: Widget<*>, node: VirtualNode<*>, indexInParent: Int) {
        reconcilers.forEach {
            it.addNode(parent, node, indexInParent)
        }
    }

    override fun deleteNode(node: Widget<*>, parent: Widget<*>) {
        reconcilers.forEach {
            it.deleteNode(node, parent)
        }
    }

    override fun changeProps(widget: Widget<*>, takePropsFrom: PropsOwner<*>) {
        reconcilers.forEach {
            it.changeProps(widget, takePropsFrom)
        }
    }

}