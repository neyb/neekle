package neekle

import neekle.inject.api.Locator

class Injector internal constructor(private val module: Module) {
    constructor(locator: Locator) : this(LocatorModule(locator))

    inline operator fun <reified T> invoke(definition: String? = null): T = get(T::class.java, definition)
    inline fun <reified T> getAll(definition: String? = null): List<T> = getAll(T::class.java, definition)

    operator fun <T> get(type: Class<T>, name: String? = null): T =
            BindingDefinition(type, name).let { definition ->
                module.getProviders(definition)
                        .single({ throw NoComponentFound(definition) },
                                { throw SeveralComponentsFound() })
                        .getComponent()
            }

    //    [type, definition]
    fun <T> getAll(type: Class<T>, definition: String? = null): List<T> = module
            .getProviders(BindingDefinition(type, definition))
            .map { it.getComponent() }
}

//TODO improve these exceptions
class NoComponentFound internal constructor(
        bindingDefinition: BindingDefinition<*>
                                          ) : Exception("no component found for $bindingDefinition")

class SeveralComponentsFound : Exception()

