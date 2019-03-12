package neekle

interface ConfigurableModule {
    fun <T> bind(target: Class<T>, name: String? = null, provider: ComponentProvider<T>)
    fun submodule(configuration: Configuration)
}