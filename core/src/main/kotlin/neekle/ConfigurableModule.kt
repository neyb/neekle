package neekle

interface ConfigurableModule {
    fun <T> bind(target: Class<T>, name: String? = null, provider: ComponentProvider<T>)
    fun <T> bindDefault(target: Class<T>, name: String?, provider: ComponentProvider<T>)
    fun submodule(configuration: Configuration)
    fun submoduleDefault(configuration: Configuration)
}