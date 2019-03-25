package neekle

internal interface Module {
    fun <T> getProviders(definition: BindingDefinition<T>): List<ComponentProvider<T>>
}

