package neekle

import neekle.inject.api.Injector
import neekle.inject.api.Locator

internal class NeekleLocator(private val module: Module) : Locator {
    val injector = Injector(this)

    override fun <T> get(type: Class<T>, definition: String?) =
            BindingCriteria(type, definition).let { criteria ->
                module.getBindings(criteria).let { bindings ->
                    when {
                        bindings.size == 1 -> bindings.single().provider.get(injector)
                        bindings.isEmpty() -> throw NoParticleFound(criteria)
                        bindings.size > 1 -> throw SeveralParticlesFound(criteria, bindings.map { it.definition })
                        else -> throw IllegalStateException("bindings has negative size ???")
                    }
                }
            }

    override fun <T> getAll(type: Class<T>, definition: String?) =
            module.getBindings(BindingCriteria(type, definition)).map { it.provider.get(injector) }
}

