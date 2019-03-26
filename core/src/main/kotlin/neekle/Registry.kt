package neekle

internal class Registry(
        private val injector: Injector
                       ) : Module {
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

    override fun <T> getDefaultProviders(definition: BindingDefinition<T>) =
            defaultBindings.componentProvidersOf(definition) + subModules.flatMap { it.getDefaultProviders(definition) }

    override fun <T> getNonDefaultProviders(definition: BindingDefinition<T>) =
            bindings.componentProvidersOf(definition) + subModules.flatMap { it.getNonDefaultProviders(definition) }

    private fun <T> List<Binding<*>>.componentProvidersOf(definition: BindingDefinition<T>) = this
            .mapNotNull { it.asCandidateForOrNull(definition) }
            .map { SimpleComponentProvider(injector, it.initializer) }

    //    override fun <T> getBindings(definition: BindingDefinition<T>): List<Binding<T>> {
    //        return bindings.mapNotNull { it.asCandidateForOrNull(definition) } +
    //                bindingFinders.flatMap { it.getBindings(definition) }
    //    }
}