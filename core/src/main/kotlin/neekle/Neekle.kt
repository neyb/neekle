package neekle

import neekle.inject.api.Injector

class Neekle(init: ModuleConfigurer.() -> Unit) {
    private val module = Module()

    init {
        module.let { ModuleConfigurer(it).apply(init) }
    }

    private val locator = NeekleLocator(module)

    val injector: Injector get() = locator.injector

    inline operator fun <reified T> invoke(name: String? = null) = injector<T>(name)
}

