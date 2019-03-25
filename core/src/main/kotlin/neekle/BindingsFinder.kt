package neekle

internal interface BindingsFinder {

    fun <T> getBinding(definition: BindingDefinition<T>): Binding<T> =
            getBindings(definition).single(
                    { throw NoBindingFound(definition) },
                    { bindings -> throw SeveralBindingsFound(definition, bindings.map { it.definition }) })

    fun <T> getBindings(definition: BindingDefinition<T>): List<Binding<T>>
}

class NoBindingFound internal constructor(definition: BindingDefinition<*>)
    : Exception("no binding found for $definition")

class SeveralBindingsFound internal constructor(
        definition: BindingDefinition<*>,
        existingMatchingDefinitions: List<BindingDefinition<*>>)
    : Exception("several potential bindings for $definition: $existingMatchingDefinitions")