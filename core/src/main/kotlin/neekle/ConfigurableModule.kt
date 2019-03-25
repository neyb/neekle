package neekle

interface ConfigurableModule {
    fun <T> bind(target: Class<T>, name: String? = null, initializer: ComponentInitializer<T>)
    fun <T> bindDefault(target: Class<T>, name: String?, initializer: ComponentInitializer<T>)
    fun submodule(configuration: Configuration)
}