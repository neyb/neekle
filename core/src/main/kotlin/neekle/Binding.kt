package neekle

internal class Binding<out T>(
        val definition: BindingDefinition<T>,
        val initializer: ComponentInitializer<T>) {
    @Suppress("UNCHECKED_CAST")
    fun <R> asCandidateForOrNull(definition: BindingDefinition<R>): Binding<R>? =
            takeIf { isCandidateFor(definition) } as? Binding<R>

    fun isCandidateFor(definition: BindingDefinition<*>): Boolean = this.definition.isCandidateFor(definition)
}