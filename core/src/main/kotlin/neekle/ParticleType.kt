package neekle

import neekle.inject.api.Injector

interface ParticleType {
    fun <T> createProvider(init: (Injector) -> T): ParticleProvider<T>
}
