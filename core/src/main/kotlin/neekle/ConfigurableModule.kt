package neekle

interface ConfigurableModule {
    fun <T> bind(target: Class<T>, name: String? = null, provider: ParticleProvider<T>)
    fun onAnyConflict(defaultAction: BindAction)
    fun <T> onConflict(type: Class<T>, bindAction: BindAction)
    fun submodule(init: ModuleConfigurer.() -> Unit)
}