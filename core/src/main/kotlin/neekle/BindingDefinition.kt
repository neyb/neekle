package neekle

internal data class BindingDefinition<out T>(val type: Class<out T>, val name: String?) {

    fun isCandidateFor(other: BindingDefinition<*>) =
            other.type.isAssignableFrom(type) &&
                    (other.name == null || name == other.name)

    override fun toString() = "${type.name}${name?.let { "($it)" }?:""}"
}