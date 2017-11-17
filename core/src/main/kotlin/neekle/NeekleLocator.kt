package neekle

import neekle.inject.api.Injector
import neekle.inject.api.Locator

internal class NeekleLocator(private val bindingsFinder: BindingsFinder) : Locator {
    val injector = Injector(this)

    override fun <T> get(type: Class<T>, name: String?) = BindingDefinition(type, name).let { definition ->
        bindingsFinder.getBindings(definition).let { bindings ->
            when (bindings.size) {
                0 -> throw NoBindingFound(definition)
                1 -> bindings.single().provider.handledGet(definition)
                else -> throw SeveralBindingsFound(definition, bindings.map { it.definition })
            }
        }
    }

    override fun <T> getAll(type: Class<T>, name: String?) = BindingDefinition(type, name).let { definition ->
        try {
            bindingsFinder.getBindings(definition).map { it.provider.handledGet(definition) }
        } catch (e: Exception) {
            throw CannotCreateComponents(definition, e)
        }
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

class NoBindingFound internal constructor(definition: BindingDefinition<*>)
    : Exception("no binding found for $definition")

class SeveralBindingsFound internal constructor(
        definition: BindingDefinition<*>,
        existingMatchingDefinitions: List<BindingDefinition<*>>)
    : Exception("several potential bindings for $definition: $existingMatchingDefinitions")