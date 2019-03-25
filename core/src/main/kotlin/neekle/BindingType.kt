package neekle

interface BindingType {
    fun <T> createProvider(init: (Injector) -> T): ComponentInitializer<T>
}
