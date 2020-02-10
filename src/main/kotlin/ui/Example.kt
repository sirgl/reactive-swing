package ui

import ui.nodes.box
import ui.nodes.button
import ui.nodes.label
import java.util.*
import javax.swing.JFrame
import javax.swing.WindowConstants

data class ExampleProps(
    val text: String,
    val number: Int
)

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

fun main() {
    val frame = JFrame("Hello")
    frame.setSize(200, 200)
    val kit = SwingKit(StderrLogger(EnumSet.allOf(LogFeature::class.java)))
    val component = ExampleComponent(ExampleProps("example", 2), kit)
    val jComponent = kit.mount(component)
    frame.add(jComponent)
    frame.isVisible = true
    frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
}