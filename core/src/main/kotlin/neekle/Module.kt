package neekle

class Module {

    private val bindings = Bindings()
    private val subModules = Modules()

    inline fun <reified T> bind(): Binder<T> = bind(T::class.java)

    fun <T> bind(target: Class<T>): Binder<T> {
        return Binder(BindingDefinition.Assignable(target), this)
    }

    internal fun <T> addBinding(binding: Binding<T>) {
        bindings.add(binding)
    }

    fun <T> getBindings(bindingCriteria: BindingCriteria<T>) : Collection<Binding<T>> {
        return bindings.matching(bindingCriteria)
    }

}