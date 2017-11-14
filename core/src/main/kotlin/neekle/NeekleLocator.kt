package neekle

import neekle.inject.api.Injector
import neekle.inject.api.Locator

internal class NeekleLocator(private val module: Module) : Locator {
    val injector = Injector(this)

    override fun <T> get(type: Class<T>, definition: String?) =
            BindingCriteria(type, definition).let { criteria ->
                try {
                    module.getBindings(criteria).let { bindings ->
                        when (bindings.size) {
                            0 -> throw NoParticleFound(criteria)
                            1 -> bindings.single().provider.get(injector)
                            else -> throw SeveralParticlesFound(criteria, bindings.map { it.definition })
                        }
                    }
                } catch (e: Exception) {
                    throw CannotCreateParticle(criteria, e)
                }
            }

    override fun <T> getAll(type: Class<T>, definition: String?) =
            module.getBindings(BindingCriteria(type, definition)).map { it.provider.get(injector) }
}

class CannotCreateParticle(val criteria: BindingCriteria<*>, e: Exception) : Exception("cannot create $criteria", e)