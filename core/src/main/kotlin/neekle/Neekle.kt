package neekle

import neekle.ModuleConfigurer.Companion.configure

class Neekle(configuration: Configuration) {
    val injector: Injector = Injector.of(configuration.configure(NeekleModule()))

    inline operator fun <reified T> invoke(name: String? = null) = injector.inject<T>(name)
}

