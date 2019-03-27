package neekle

internal class Registry {
    private val bindings = mutableListOf<Binding<*>>()
    private val defaultBindings = mutableListOf<Binding<*>>()
    private val subModules = mutableListOf<Module>()

    fun <T> add(binding: Binding<T>) {
        bindings.add(binding)
    }

    fun <T> addDefault(binding: Binding<T>) {
        defaultBindings.add(binding)
    }

    fun add(subModule: Module) {
        subModules += subModule
    }

    fun <T> getDefaultProviders(definition: BindingDefinition<T>, injector: Injector) =
            defaultBindings.componentProvidersOf(definition, injector) +
                    subModules.flatMap { it.getDefaultProviders(definition) }

    fun <T> getNonDefaultProviders(definition: BindingDefinition<T>, injector: Injector) =
            bindings.componentProvidersOf(definition, injector) +
                    subModules.flatMap { it.getNonDefaultProviders(definition) }

    private fun <T> List<Binding<*>>.componentProvidersOf(definition: BindingDefinition<T>, injector: Injector) =
            mapNotNull { it.asCandidateForOrNull(definition) }
                    .map { SimpleComponentProvider(injector, it.initializer) }
}