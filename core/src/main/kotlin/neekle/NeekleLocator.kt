package neekle

import neekle.inject.api.Injector
import neekle.inject.api.Locator

internal class NeekleLocator(private val module: Module) : Locator {
    val injector = Injector(this)

    override fun <T> get(type: Class<T>, definition: String?) = BindingDefinition(type, definition)
            .let { handledGet(it) { getSingleParticle(it) } }

    override fun <T> getAll(type: Class<T>, definition: String?) = BindingDefinition(type, definition).let { criteria ->
        module.getBindings(criteria).map { handledGet(criteria) { it.provider.get(injector) } }
    }

    private fun <T> getSingleParticle(definition: BindingDefinition<T>) =
            module.getBindings(definition).let { bindings ->
                when (bindings.size) {
                    0 -> throw NoParticleFound(definition)
                    1 -> bindings.single().provider.get(injector)
                    else -> throw SeveralParticlesFound(definition, bindings.map { it.definition })
                }
            }

    private inline fun <T> handledGet(definition: BindingDefinition<T>, block: () -> T): T =
            try {
                block()
            } catch (e: Exception) {
                throw CannotCreateParticle(definition, e)
            }
}

class CannotCreateParticle(
        val definition: BindingDefinition<*>,
        e: Exception) : Exception("cannot create $definition", e)

class NoParticleFound(val definition: BindingDefinition<*>) : Exception()

class SeveralParticlesFound(val definition: BindingDefinition<*>,existingMatchingDefinitions: List<BindingDefinition<*>>)
    : Exception("several potential particule for $definition: $existingMatchingDefinitions")