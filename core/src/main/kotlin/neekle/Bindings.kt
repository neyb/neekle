package neekle

internal class Bindings {
    private val bindings = mutableListOf<Binding<*>>()
    private val defaultBindings = mutableListOf<Binding<*>>()

    fun <T> add(binding: Binding<T>) {
        bindings.add(binding)
    }

    fun <T> addDefault(binding: Binding<T>) {
        defaultBindings.add(binding)
    }

    fun <T> matching(definition: BindingDefinition<T>) =
            bindings.mapNotNull { it.asCandidateForOrNull(definition) }.takeIf { it.isNotEmpty() }
                    ?: defaultBindings.mapNotNull { it.asCandidateForOrNull(definition) }
}

