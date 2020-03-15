package ui

import kotlin.reflect.KMutableProperty0

interface Binding<T: Any> {
    fun set(value: T)
    fun get() : T
}

class StateAwareBinding {

}

/**
 * @param property must belong to state of the object
 */
fun <State : Any, Props : Any, T: Any> StatefulComponent<Props, State>.bind(property: KMutableProperty0<T>) : Binding<T> {
    return object : Binding<T> {
        override fun set(value: T) {
            changeState {
                property.set(value)
            }
        }

        override fun get(): T {
            TODO("Not yet implemented")
        }
    }
}