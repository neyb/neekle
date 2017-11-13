package neekle

enum class BindAction {
    ignore, add, replace, fail;

    val always: PolicyElement by lazy {
        object : PolicyElement {
            override fun actionFor(existingDefinintion: Collection<BindingDefinition>, addingCriteria: BindingCriteria<*>) =
                    this@BindAction
        }
    }
}