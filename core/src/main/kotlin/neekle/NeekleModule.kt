package neekle

import neekle.ModuleConfigurer.Companion.configure

internal class NeekleModule internal constructor(
        private val parentInjector: Injector?
                                                ) : ConfigurableModule, Module {
    private val injector: Injector = Injector(this)
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

    override fun submodule(configuration: Configuration) {
        registry.add(configuration.configure(NeekleModule(injector)))
    }

    override fun <T> getProviders(definition: BindingDefinition<T>) = registry.getProviders(definition)
}