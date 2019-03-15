package neekle

import neekle.inject.api.Injector
import neekle.inject.api.Locator

internal class ModuleLocator(private val bindingsFinder: BindingsFinder) : Locator {
    val injector = Injector(this)

    override fun <T> get(type: Class<T>, name: String?) = BindingDefinition(type, name).let { definition ->
        bindingsFinder.getBinding(definition).provider.handledGet(definition)
    }

    override fun <T> getAll(type: Class<T>, name: String?) = BindingDefinition(type, name).let { definition ->
        bindingsFinder.getBindings(definition).map { it.provider.handledGet(definition) }
    }

    private fun <T> ComponentProvider<T>.handledGet(definition: BindingDefinition<T>): T =
            try {
                get(injector)
            } catch (e: Exception) {
                throw CannotCreateComponent(definition, e)
            }
}

class CannotCreateComponent internal constructor(definition: BindingDefinition<*>, e: Exception)
    : Exception("cannot create $definition", e)

class CannotCreateComponents internal constructor(definition: BindingDefinition<*>, e: Exception)
    : Exception("cannot create several $definition", e)
