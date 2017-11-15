package neekle

internal interface BindingsFinder {
    fun <T> getBindings(definition: BindingDefinition<T>): List<Binding<T>>
}