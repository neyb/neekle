package neekle

import neekle.ModuleConfigurer.Companion.configure

internal class NeekleModule internal constructor(
        parents: List<NeekleModule> = emptyList()
        //        ,
        //        TODO injector not used...
        //        private val parentInjector: Injector?
                                                ) : ConfigurableModule, Module {
    private val modules = parents + this
    internal val injector: Injector = Injector(modules)
    private val registry = Registry(injector)
    private val internalRegistry = Registry(injector)

    override fun <T> bind(target: Class<T>, name: String?, initializer: ComponentInitializer<T>) {
        val definition = BindingDefinition(target, name)
        registry.add(Binding(definition, initializer))
    }

    override fun <T> bindDefault(target: Class<T>, name: String?, initializer: ComponentInitializer<T>) {
        val definition = BindingDefinition(target, name)
        registry.addDefault(Binding(definition, initializer))
    }

    override fun <T> bindInternal(target: Class<T>, name: String?, initializer: ComponentInitializer<T>) {
        TODO("not implemented")
    }

    override fun <T> bindInternalDefault(target: Class<T>, name: String?, initializer: ComponentInitializer<T>) {
        TODO("not implemented")
    }

    override fun submodule(configuration: Configuration) {
        registry.add(configuration.configure(NeekleModule(modules)))
    }

    override fun <T> getNonDefaultProviders(definition: BindingDefinition<T>) = registry.getNonDefaultProviders(definition)
    override fun <T> getDefaultProviders(definition: BindingDefinition<T>) = registry.getDefaultProviders(definition)
}