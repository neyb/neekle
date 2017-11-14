package neekle

import neekle.inject.api.Injector

//TODO not threadsafe
class Singleton<out T>(private val init: (Injector) -> T) : ParticleProvider<T> {
    private var instance: T? = null

    override fun get(injector: Injector) = instance ?: init(injector).also { instance = it }
}