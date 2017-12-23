package neekle

import neekle.inject.api.Injector

internal object Prototype : BindingType {
    override fun <T> createProvider(init: (Injector) -> T) = object : ComponentProvider<T> {
        override fun get(injector: Injector) = init(injector)
    }
}