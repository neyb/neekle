package neekle

import neekle.inject.api.Injector

//TODO not threadsafe
class Singleton<T>(private val init: (Injector) -> T) : ParticleProvider<T> {
    var instance: T? = null

    override fun get(injector: Injector) = instance ?: init(injector).also { instance = it }
}