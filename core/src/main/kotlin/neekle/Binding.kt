package neekle

internal class Binding<out T>(
        val definition: BindingDefinition<T>,
        val provider: ComponentProvider<T>) {
    @Suppress("UNCHECKED_CAST")
    fun <R> asCandidateForOrNull(definition: BindingDefinition<R>) =
            if (isCandidateFor(definition)) this as Binding<R>
            else null

    fun isCandidateFor(definition: BindingDefinition<*>): Boolean = this.definition.isCandidateFor(definition)
}