package neekle

internal class Modules {
    private val modules = mutableListOf<Module>()
    private val defaultModules = mutableListOf<Module>()
    fun add(module: Module) = apply { modules.add(module) }
    fun addDefault(module: Module) = apply { defaultModules.add(module) }
}