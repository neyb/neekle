package neekle

import neekle.inject.api.Injector

class Binder<in T>(private val bind: (ParticleProvider<T>) -> Unit) {

    infix fun to(provider: ParticleProvider<T>) = bind(provider)
    infix fun to(instance: T) = to(InstanceProvider(instance))

    private class InstanceProvider<out T>(private val instance: T) : ParticleProvider<T> {
        override fun get(injector: Injector) = instance
    }
}