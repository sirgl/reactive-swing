package ui

interface UiKit {
    fun createNativeWidget(node: VirtualNode<*>) : Widget<*>
    fun reconcile()
    fun isReconciliationInProgress() : Boolean
}