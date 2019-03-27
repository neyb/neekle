package neekle

interface BindingType {
    fun <T> createInitializer(init: (Injector) -> T): ComponentInitializer<T>
}
