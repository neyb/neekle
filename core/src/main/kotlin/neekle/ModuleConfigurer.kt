package neekle

typealias Configuration = ModuleConfigurer.() -> Unit

class ModuleConfigurer private constructor(module: ConfigurableModule) : ConfigurableModule by module {

    internal companion object {
        internal fun Configuration.configure(neekleModule: NeekleModule) = neekleModule.also {
            ModuleConfigurer(it).configure(this)
        }
    }

    val singleton: BindingType = Singleton
    val prototype: BindingType = Prototype
    private val delayed = mutableListOf<Configuration>()

    fun defaultModule(configuration: Configuration) {
        delay { submodule(configuration) }
    }

    inline fun <reified T> bind(
            name: String? = null,
            bindingType: BindingType = singleton,
            noinline init: Injector.() -> T) =
            bind(T::class.java, name, bindingType.createProvider(init))

    inline fun <reified T> bindDefault(
            name: String? = null,
            bindingType: BindingType = singleton,
            noinline init: Injector.() -> T) =
            bindDefault(T::class.java, name, bindingType.createProvider(init))

    inline fun <reified T> bindInternal(
            name: String? = null,
            bindingType: BindingType = singleton,
            noinline init: Injector.() -> T) =
            bindInternal(T::class.java, name, bindingType.createProvider(init))

    inline fun <reified T> bindInternalDefault(
            name: String? = null,
            bindingType: BindingType = singleton,
            noinline init: Injector.() -> T) =
            bindInternalDefault(T::class.java, name, bindingType.createProvider(init))

    private fun configure(configuration: Configuration) {
        this.configuration()
        runDelayed()
    }

    private fun delay(configuration: Configuration) {
        delayed += configuration
    }

    private fun runDelayed() = delayed.forEach { this.it() }
}