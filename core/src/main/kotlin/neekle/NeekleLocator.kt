package neekle

import neekle.inject.api.Injector
import neekle.inject.api.Locator

internal class NeekleLocator(private val bindingsFinder: BindingsFinder) : Locator {
    val injector = Injector(this)

    override fun <T> get(type: Class<T>, name: String?) = BindingDefinition(type, name).let { definition ->
        bindingsFinder.getBindings(definition).let { bindings ->
            when (bindings.size) {
                0 -> throw NoParticleFound(definition)
                1 -> bindings.single().provider.handledGet(definition)
                else -> throw SeveralParticlesFound(definition, bindings.map { it.definition })
            }
        }
    }

    override fun <T> getAll(type: Class<T>, name: String?) = BindingDefinition(type, name).let { definition ->
        try {
            bindingsFinder.getBindings(definition).map { it.provider.handledGet(definition) }
        } catch (e: Exception) {
            throw CannotCreateParticles(definition, e)
        }
    }

    private fun <T> ParticleProvider<T>.handledGet(definition: BindingDefinition<T>): T =
            try {
                get(injector)
            } catch (e: Exception) {
                throw CannotCreateParticle(definition, e)
            }
}

class CannotCreateParticle internal constructor(definition: BindingDefinition<*>, e: Exception)
    : Exception("cannot create $definition", e)

class CannotCreateParticles internal constructor(definition: BindingDefinition<*>, e: Exception)
    : Exception("cannot create several $definition", e)

class NoParticleFound internal constructor(definition: BindingDefinition<*>)
    : Exception("no particle found for $definition")

class SeveralParticlesFound internal constructor(
        definition: BindingDefinition<*>,
        existingMatchingDefinitions: List<BindingDefinition<*>>)
    : Exception("several potential particule for $definition: $existingMatchingDefinitions")