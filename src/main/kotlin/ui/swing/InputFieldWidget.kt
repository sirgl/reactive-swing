package ui.swing

import ui.LeafWidget
import ui.UiKit
import ui.VirtualNodeType
import ui.nodes.InputFieldProps
import ui.nodes.InputFieldType
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JComponent
import javax.swing.JTextField

// This must be connected to some variable in state
class InputFieldWidget(private val kit: UiKit, props: InputFieldProps) : LeafWidget<InputFieldProps>(props), SwingComponentOwner {
    private val input = JTextField()
    private var listener: ActionListener? = null

    override val type: VirtualNodeType<InputFieldProps>
        get() = InputFieldType

    override val component: JComponent
        get() = input

    override fun updateFromProps(newProps: InputFieldProps) {

        input.text = props.binding.get()
        if (listener != null) {
            input.removeActionListener(listener)
        }
        listener = object: ActionListener {
            override fun actionPerformed(e: ActionEvent) {
                props.binding.set(input.text)
                kit.reconcile()
            }
        }
        input.addActionListener(listener)
    }
}