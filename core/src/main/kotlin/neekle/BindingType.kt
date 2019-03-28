package neekle

interface BindingType {
    fun <T> createInitializer(init: (Injector) -> T): ComponentInitializer<T>
}

internal object Singleton : BindingType {
    override fun <T> createInitializer(init: (Injector) -> T): ComponentInitializer<T> = SingletonInitializer(init)
}

internal object Prototype : BindingType {
    override fun <T> createInitializer(init: (Injector) -> T) = object : ComponentInitializer<T> {
        override fun get(injector: Injector) = init(injector)
    }
}

//TODO not threadsafe
private class SingletonInitializer<out T>(private val init: (Injector) -> T) : ComponentInitializer<T> {
    private var instance: T? = null

    override fun get(injector: Injector) = instance ?: init(injector).also { instance = it }
}