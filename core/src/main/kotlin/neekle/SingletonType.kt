package neekle

import neekle.inject.api.Injector

val Module.singleton: ParticleType get() = SingletonType

object SingletonType : ParticleType {
    override fun <T> createProvider(init: (Injector) -> T) = Singleton(init)
}
