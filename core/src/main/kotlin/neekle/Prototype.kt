package neekle

internal object Prototype : BindingType {
    override fun <T> createProvider(init: (Injector) -> T) = object : ComponentInitializer<T> {
        override fun get(injector: Injector) = init(injector)
    }
}