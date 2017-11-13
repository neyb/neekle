package neekle

class Bindings {
    private val bindings = mutableListOf<Binding<*>>()

    fun <T> add(binding: Binding<T>) {
        bindings.add(binding)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> matching(criteria: BindingCriteria<T>) =
            bindings.filter { it.match(criteria) }
                    .map { it as Binding<T> }

    fun <T> replace(criteria: BindingCriteria<T>, binding: Binding<T>) {
        bindings.removeIf { it.match(criteria) }
        add(binding)
    }
}

