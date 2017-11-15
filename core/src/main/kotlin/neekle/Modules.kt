package neekle

internal class Modules {
    private val modules = mutableListOf<Module>()
    fun add(module: Module) = apply { modules.add(module) }
    fun <T> matching(definition: BindingDefinition<T>): List<Binding<T>> =
            modules.flatMap { it.getBindings(definition) }
}