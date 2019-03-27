package neekle

internal object Singleton : BindingType {
    override fun <T> createInitializer(init: (Injector) -> T): ComponentInitializer<T> = SingletonInitializer(init)
}

//TODO not threadsafe
private class SingletonInitializer<out T>(private val init: (Injector) -> T) : ComponentInitializer<T> {
    private var instance: T? = null

    override fun get(injector: Injector) = instance ?: init(injector).also { instance = it }
}