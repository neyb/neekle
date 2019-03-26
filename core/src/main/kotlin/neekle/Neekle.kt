package neekle

import neekle.ModuleConfigurer.Companion.configure

class Neekle(configuration: Configuration) {
    //    private val module = configuration.configure(NeekleModule(null))
    //    private val locator = ModuleLocator(module)
    //    val injector: Injector get() = Injector(locator)

    val injector: Injector = configuration.configure(NeekleModule()).injector

    inline operator fun <reified T> invoke(name: String? = null) = injector.inject<T>(name)
}

