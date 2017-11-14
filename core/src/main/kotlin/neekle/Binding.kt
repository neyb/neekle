package neekle

internal class Binding<out T>(val definition: BindingDefinition<T>, val provider: ParticleProvider<T>) {
    fun isCandidateFor(definition: BindingDefinition<*>): Boolean = this.definition.isCandidateFor(definition)
}