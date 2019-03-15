package neekle

import neekle.ModuleConfigurer.Companion.configure

internal class Module internal constructor() : ConfigurableModule, BindingsFinder {
    private val defaultRegistry = Registry()

//    private val bindings: Bindings = Bindings()
//    private val subModules = Modules()

    override fun <T> bind(target: Class<T>, name: String?, provider: ComponentProvider<T>) {
        val definition = BindingDefinition(target, name)
        bindings.add(Binding(definition, provider))
    }

    override fun <T> bindDefault(target: Class<T>, name: String?, provider: ComponentProvider<T>) {
        val definition = BindingDefinition(target, name)
        bindings.add(Binding(definition, provider))
    }

    override fun submodule(configuration: Configuration) {
        subModules.add(configuration.configure(Module()))
    }

    override fun submoduleDefault(configuration: Configuration) {
        subModules.addDefault(configuration.configure(Module()))
    }

    override fun <T> getBindings(definition: BindingDefinition<T>) =
//            bindings.matching(definition)
}