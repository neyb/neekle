package neekle

interface ComponentInitializer<out T> {
    fun get(injector: Injector): T
}

