package neekle

import neekle.inject.api.Injector

typealias Configuration = ModuleConfigurer.() -> Unit

class ModuleConfigurer private constructor(module: ConfigurableModule) : ConfigurableModule by module {

    internal companion object {
        internal fun Configuration.configure(module: Module = Module()) = module.also {
            ModuleConfigurer(it).configure(this)
        }
    }

    val singleton: BindingType = Singleton
    private val delayed = mutableListOf<Configuration>()

    fun defaultModule(configuration: Configuration) {
        delay { submodule(configuration) }
    }

    inline fun <reified T> bind(
            name: String? = null,
            bindingType: BindingType = singleton,
            noinline init: (Injector) -> T) =
            bind(T::class.java, name, bindingType.createProvider(init))

    inline fun <reified T> onConflict(bindAction: BindAction) = onConflict(T::class.java, bindAction)

    private fun configure(configuration: Configuration) {
        this.configuration()
        runDelayed()
    }

    private fun delay(configuration: Configuration) {
        delayed += configuration
    }

    private fun runDelayed() = delayed.forEach { this.it() }
}