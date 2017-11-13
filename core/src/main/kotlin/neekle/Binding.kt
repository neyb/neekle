package neekle

class Binding<out T>(
        val definition: BindingDefinition,
        val provider: ParticleProvider<T>) {
    fun match(criteria: BindingCriteria<*>): Boolean = definition.match(criteria)
}