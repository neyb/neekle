package neekle

import neekle.ModuleConfigurer.Companion.configure

internal interface Module {
    fun <T> getNonDefaultProviders(definition: BindingDefinition<T>): List<ComponentProvider<T>>
    fun <T> getDefaultProviders(definition: BindingDefinition<T>): List<ComponentProvider<T>>
}

internal class NeekleModule(private val parents: List<NeekleModule> = emptyList()) : ConfigurableModule, Module {
    private val registry = Registry()
    private val internalRegistry = Registry()
    private val injector = Injector.of(
            parents.flatMap { listOf(it.registry, it.internalRegistry) } + registry + internalRegistry)

    override fun <T> bind(target: Class<T>, name: String?, initializer: ComponentInitializer<T>) {
        val definition = BindingDefinition(target, name)
        registry.add(Binding(definition, initializer))
    }

    override fun <T> bindDefault(target: Class<T>, name: String?, initializer: ComponentInitializer<T>) {
        val definition = BindingDefinition(target, name)
        registry.addDefault(Binding(definition, initializer))
    }

    override fun <T> bindInternal(target: Class<T>, name: String?, initializer: ComponentInitializer<T>) {
        internalRegistry.add(Binding(BindingDefinition(target, name), initializer))
    }

    override fun <T> bindInternalDefault(target: Class<T>, name: String?, initializer: ComponentInitializer<T>) {
        internalRegistry.addDefault(Binding(BindingDefinition(target, name), initializer))
    }

    override fun submodule(configuration: Configuration) {
        registry.add(configuration.configure(NeekleModule(parents + this)))
    }

    override fun <T> getNonDefaultProviders(definition: BindingDefinition<T>) =
            registry.getNonDefaultProviders(definition, this.injector)

    override fun <T> getDefaultProviders(definition: BindingDefinition<T>) =
            registry.getDefaultProviders(definition, this.injector)
}