package neekle

import neekle.inject.api.Injector

internal object Singleton : BindingType {
    override fun <T> createProvider(init: (Injector) -> T): ComponentProvider<T> = SingletonProvider(init)
}

//TODO not threadsafe
private class SingletonProvider<out T>(private val init: (Injector) -> T) : ComponentProvider<T> {
    private var instance: T? = null

    override fun get(injector: Injector) = instance ?: init(injector).also { instance = it }
}