package ui.swing

import ui.LeafWidget
import ui.VirtualNodeType
import ui.Widget
import ui.nodes.ButtonProps
import ui.nodes.ButtonType
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JComponent

class ButtonWidget(
    props: ButtonProps
) : LeafWidget<ButtonProps>(props), SwingComponentOwner {
    private val button = JButton()
    private var listener: ActionListener? = null

    override fun updateFromProps(newProps: ButtonProps) {
        button.text = newProps.label
        if (listener != null) {
            button.removeActionListener(listener)
        }
        @Suppress("ObjectLiteralToLambda") // Lambda has not the same identity as object passed to function
        val listener = object: ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                newProps.onClick()
            }

        }
        button.addActionListener(listener)
        this.listener = listener
    }

    override val children: List<Widget<*>>
        get() = emptyList()
    override val type: VirtualNodeType<ButtonProps>
        get() = ButtonType
    override val component: JComponent
        get() = button
}