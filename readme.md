# Reactive swing library

An attempt to make library to create UI in Swing easily.

Example:
```kotlin
class ExampleComponent(props: ExampleProps, kit: UiKit) : StatefulComponent<ExampleProps, Int>(props, 2, kit) {
    override fun RenderContext.render() {
        box {
            label(state.toString())
            box {
                for (i in 0..state) {
                    label(i.toString())
                }
            }
            button("add", onClick = {
                setState { state + 1 }
            })
            button("remove", onClick = {
                setState { state - 1 }
            })
        }
    }
}
```

Things that work:
* Basic reconciliation
* Changes detection for VM (using cells and read tracking)
* Basic primitives

TODO:
* Input field
* Placement/Alignment strategies
* Horizontal box
* Component size management
