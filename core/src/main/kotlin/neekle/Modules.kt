package neekle

internal class Modules {
    private val modules = mutableListOf<Module>()
    fun add(module: Module) = apply { modules.add(module) }
}