package neekle

import neekle.inject.api.Injector

val Module.singleton: ParticleType get() = Singleton

private object Singleton : ParticleType {
    override fun <T> createProvider(init: (Injector) -> T) = SingletonProvider(init)
}

//TODO not threadsafe
private class SingletonProvider<out T>(private val init: (Injector) -> T) : ParticleProvider<T> {
    private var instance: T? = null

    override fun get(injector: Injector) = instance ?: init(injector).also { instance = it }
}