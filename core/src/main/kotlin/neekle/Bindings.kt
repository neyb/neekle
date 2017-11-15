package neekle

internal class Bindings {
    private val bindings = mutableListOf<Binding<*>>()

    fun <T> add(binding: Binding<T>) {
        bindings.add(binding)
    }

    fun <T> matching(definition: BindingDefinition<T>) = bindings.mapNotNull { it.asCandidateForOrNull(definition) }

    fun <T> replace(binding: Binding<T>) {
        bindings.removeIf { it.isCandidateFor(binding.definition) }
        add(binding)
    }

    fun inConflict(definition: BindingDefinition<*>): List<Binding<*>> =
            bindings.filter { it.isCandidateFor(definition) || definition.isCandidateFor(it.definition) }
}

