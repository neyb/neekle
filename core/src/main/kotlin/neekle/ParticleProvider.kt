package neekle

import neekle.inject.api.Injector

interface ParticleProvider<out T> {
    fun get(injector: Injector): T
}