# Reactive swing library

An attempt to make library to create UI in Swing easily using virtual DOM. Inspired by React/JS.

Example:
```kotlin
class ExampleComponent(props: ExampleProps, kit: UiKit) : StatefulComponent<ExampleProps, Int>(props, 2, kit) {
    private fun handleAdd() {
        setState { state + 1 }
    }

    private fun handleRemove() {
        setState { state - 1 }
    }

    override fun RenderContext.render() {
        box {
            label("Total: $state")
            box {
                for (i in 0 until state) {
                    label(i.toString())
                }
            }
            button("add", onClick = ::handleAdd)
            button("remove", onClick = ::handleRemove)
        }
    }
}
```

Library mostly not bound to Swing, so theoretically it is possible to utilize other rendering backends (e. g. DOM).

Things that work:
* Basic reconciliation
* Changes detection for VM (using cells and read tracking)
* Basic primitives

TODO:
* Input field
* Placement/Alignment strategies
* Horizontal box
* Component size management
* Custom reconciliations
* Keys to help reconciliation
* Batch updates
* More fine-grained change detection
* Stop recomputation calculation when results are equal 
* ...
