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