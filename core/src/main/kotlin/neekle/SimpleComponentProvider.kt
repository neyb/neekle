package neekle

interface ComponentProvider<out T> {
    fun getComponent(): T
}

//TODO rename me
class SimpleComponentProvider<out T>(
        private val injector: Injector,
        private val componentInitializer: ComponentInitializer<T>
                                    ) : ComponentProvider<T> {
    override fun getComponent(): T = componentInitializer.get(injector)
}

class AlreadyCreatedComponentProvider<out T>(private val component: T) : ComponentProvider<T> {
    override fun getComponent() = component
}