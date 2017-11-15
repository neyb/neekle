package neekle

import neekle.inject.api.Injector


class ModuleConfigurer internal constructor(module: ConfigurableModule) : ConfigurableModule by module {
    val singleton: ParticleType = Singleton

    inline fun <reified T> bind(
            name: String? = null,
            particleType: ParticleType = singleton,
            noinline init: (Injector) -> T) =
            bind(T::class.java, name, particleType.createProvider(init))

    inline fun <reified T> onConflictOf(bindAction: BindAction) = onConflictOf(T::class.java, bindAction)
}