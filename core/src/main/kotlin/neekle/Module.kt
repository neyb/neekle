package neekle

import neekle.BindAction.*
import neekle.ModuleConfigurer.Companion.configure

internal class Module internal constructor(
        private val bindings: Bindings = Bindings()
                                          ) : ConfigurableModule, BindingsFinder {

    private val subModules = Modules()

    override fun <T> bind(target: Class<T>, name: String?, provider: ComponentProvider<T>) {
        val definition = BindingDefinition(target, name)
        bindings.add(Binding(definition, provider))
    }

    override fun submodule(configuration: Configuration) {
        subModules.add(configuration.configure(Module(bindings)))
    }

    override fun <T> getBindings(definition: BindingDefinition<T>) = bindings.matching(definition)
}