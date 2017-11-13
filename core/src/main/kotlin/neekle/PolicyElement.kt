package neekle

interface PolicyElement {
    fun actionFor(existingDefinintion: Collection<BindingDefinition>, addingCriteria: BindingCriteria<*>) : BindAction?
}