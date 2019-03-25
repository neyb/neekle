package neekle

import neekle.inject.api.Locator

internal class LocatorModule(private val locator: Locator) : Module {
    override fun <T> getProviders(definition: BindingDefinition<T>) =
            locator.getAll(definition.type, definition.name).map { AlreadyCreatedComponentProvider(it) }
}