package neekle

class Injector private constructor(private val locator: Locator) {

    internal companion object {
        fun of(module: Module) = Injector(ModuleLocator(module))
        fun of(registries: Iterable<Registry>) = Injector(RegistriesLocator(registries))
    }

    inline fun <reified T> get(name: String? = null): T = get(T::class.java, name)
    inline fun <reified T> getAll(name: String? = null): List<T> = getAll(T::class.java, name)

    inline fun <reified T> inject(name: String? = null): T = get(name)
    inline fun <reified T> lazy(name: String? = null): () -> T = { get(name) }

    fun <T> get(type: Class<T>, name: String? = null): T =
            BindingDefinition(type, name).let { definition ->
                locator.getProviders(definition, this)
                        .single({ throw NoComponentFound(definition) },
                                { providers -> throw SeveralComponentsFound(definition, providers) })
                        .handledGetComponent(definition)
            }

    fun <T> getAll(type: Class<T>, name: String? = null): List<T> =
            BindingDefinition(type, name).let { definition ->
                locator.getProviders(definition, this).map { it.handledGetComponent(definition) }
            }

    private fun <T> ComponentProvider<T>.handledGetComponent(definition: BindingDefinition<*>) =
            try {
                getComponent()
            } catch (e: Exception) {
                throw CannotCreateComponent(definition, e)
            }

}

private interface Locator {
    fun <T> getProviders(definition: BindingDefinition<T>, injector: Injector): List<ComponentProvider<T>>
}

private class ModuleLocator(private val module: Module) : Locator {
    override fun <T> getProviders(definition: BindingDefinition<T>, injector: Injector) =
            module.getNonDefaultProviders(definition).ifEmpty { module.getDefaultProviders(definition) }
}

private class RegistriesLocator(private val registries: Iterable<Registry>) : Locator {
    override fun <T> getProviders(definition: BindingDefinition<T>, injector: Injector) =
            registries.flatMap { it.getNonDefaultProviders(definition, injector) }
                    .ifEmpty { registries.flatMap { it.getDefaultProviders(definition, injector) } }
}

//TODO improve these exceptions
class NoComponentFound internal constructor(bindingDefinition: BindingDefinition<*>)
    : Exception("no component found for $bindingDefinition")

class SeveralComponentsFound internal constructor(
        bindingDefinition: BindingDefinition<*>,
        providers: Collection<ComponentProvider<*>>)
    : Exception("${providers.size} components found for $bindingDefinition: $providers")

class CannotCreateComponent internal constructor(definition: BindingDefinition<*>, e: Exception)
    : Exception("cannot create $definition", e)

private fun <T> Collection<T>.single(defaultProvider: () -> T, severalFilter: (Collection<T>) -> T): T = when (size) {
    0 -> defaultProvider()
    1 -> first()
    else -> severalFilter(this)
}
