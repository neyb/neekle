package neekle

import neekle.inject.api.Injector

class Neekle(init: Module.() -> Unit) {
    private val module = Module()

    init {
        module.init()
    }

    private val locator = NeekleLocator(module)

    val injector: Injector get() = locator.injector

    inline operator fun <reified T> invoke() = injector<T>()
}

