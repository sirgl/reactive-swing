package ui

import ui.nodes.box
import ui.nodes.button
import ui.nodes.inputField
import ui.nodes.label
import java.util.*
import javax.swing.JFrame
import javax.swing.WindowConstants

data class ExampleProps(
    val text: String,
    val number: Int
)

class ExampleState(
    var count: Int,
    var text: String
)

class ExampleComponent(props: ExampleProps, kit: UiKit) : StatefulComponent<ExampleProps, ExampleState>(props, ExampleState(2, "foo"), kit) {
    private fun handleAdd() {
        changeState {
            count++
        }
    }

    private fun handleRemove() {
        changeState {
            count--
        }
    }

    override fun RenderContext.render() {
        box {
            label("Total: ${state.count}")
            box {
                for (i in 0 until state.count) {
                    label(i.toString())
                }
            }
            button("add", onClick = ::handleAdd)
            button("remove", onClick = ::handleRemove)
            inputField(state::text) // TODO here I should notify that text changes
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