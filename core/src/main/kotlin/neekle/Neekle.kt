package neekle

import neekle.inject.api.Injector
import neekle.ModuleConfigurer.Companion.configure

class Neekle(configuration: Configuration) {
    private val module = configuration.configure()
    private val locator = NeekleLocator(module)

    val injector: Injector get() = locator.injector

    inline operator fun <reified T> invoke(name: String? = null) = injector<T>(name)
}

