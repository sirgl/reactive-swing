package ui

abstract class StatefulComponent<Props: Any, State: Any>(
    props: Props,
    state: State,
    kit: UiKit
) : Component<Props>(props, kit) {
    var state: State = state
        private set

    fun changeState(f: State.() -> Unit) {
        with(state) {
            f()
        }
        if (!kit.isReconciliationInProgress()) {
            kit.reconcile()
        }
    }
}