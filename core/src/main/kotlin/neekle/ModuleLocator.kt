package neekle

import neekle.inject.api.Locator

internal class ModuleLocator(private val module: Module) : Locator {

    //FIXME if only 1 component is asked and several component match,
    // all compoents of same type will be created before the error is thrown...
    // implement me to solve this
    //    override fun <T> Locator.get(type: Class<T>, name: String?): T {
    //        TODO("not implemented")
    //    }

    override fun <T> getAll(type: Class<T>, name: String?): Collection<T> =
            BindingDefinition(type, name).let { definition ->
                module.getProviders(definition).map {
                    try {
                        it.getComponent()
                    } catch (e: Exception) {
                        throw CannotCreateComponent(definition, e)
                    }
                }
            }
}

class CannotCreateComponent internal constructor(definition: BindingDefinition<*>, e: Exception)
    : Exception("cannot create $definition", e)
