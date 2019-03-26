package neekle

internal interface Module {
    fun <T> getNonDefaultProviders(definition: BindingDefinition<T>): List<ComponentProvider<T>>
    fun <T> getDefaultProviders(definition: BindingDefinition<T>): List<ComponentProvider<T>>
}

