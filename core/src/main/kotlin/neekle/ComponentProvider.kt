package neekle

import neekle.inject.api.Injector

interface ComponentProvider<out T> {
    fun get(injector: Injector): T
}