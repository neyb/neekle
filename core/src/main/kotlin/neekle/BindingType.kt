package neekle

import neekle.inject.api.Injector

interface BindingType {
    fun <T> createProvider(init: (Injector) -> T): ComponentProvider<T>
}
