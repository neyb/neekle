package neekle


class Injector internal constructor(private val modules: List<NeekleModule>) {

    //    constructor(locator: Locator) : this(LocatorModule(locator))

    inline fun <reified T> get(name: String? = null): T = get(T::class.java, name)
    inline fun <reified T> getAll(name: String? = null): List<T> = getAll(T::class.java, name)

    inline fun <reified T> inject(name: String? = null): T = get(name)
    inline fun <reified T> lazy(name: String? = null): () -> T = { get(name) }

    fun <T> get(type: Class<T>, name: String? = null): T =
            BindingDefinition(type, name).let { definition ->
                getProviders(definition)
                        .single({ throw NoComponentFound(definition) },
                                { providers -> throw SeveralComponentsFound(definition, providers) })
                        .handledGetComponent(definition)
            }

    //    [type, definition]
    fun <T> getAll(type: Class<T>, name: String? = null): List<T> =
            BindingDefinition(type, name).let { definition ->
                getProviders(definition).map { it.handledGetComponent(definition) }
            }

    private fun <T> getProviders(definition: BindingDefinition<T>) =
            modules.flatMap { it.getNonDefaultProviders(definition) }
                    .ifEmpty { modules.flatMap { it.getDefaultProviders(definition) } }

    private fun <T> ComponentProvider<T>.handledGetComponent(definition: BindingDefinition<*>) =
            try {
                getComponent()
            } catch (e: Exception) {
                throw CannotCreateComponent(definition, e)
            }

}

//TODO improve these exceptions
class NoComponentFound internal constructor(
        bindingDefinition: BindingDefinition<*>
                                           ) : Exception("no component found for $bindingDefinition")

class SeveralComponentsFound internal constructor(
        bindingDefinition: BindingDefinition<*>,
        providers: Collection<ComponentProvider<*>>
                                                 ) : Exception("${providers.size} components found for $bindingDefinition: $providers")

class CannotCreateComponent internal constructor(definition: BindingDefinition<*>, e: Exception)
    : Exception("cannot create $definition", e)
