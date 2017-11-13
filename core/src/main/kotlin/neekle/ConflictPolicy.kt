package neekle

class ConflictPolicy(private val parent: ConflictPolicy?) {
    private val elements = mutableListOf<PolicyElement>()
    private var defaultPolicyElement: PolicyElement? = null

    fun actionFor(existingDefinintion: Collection<BindingDefinition>, addingCriteria: BindingCriteria<*>) =
            actionForNoDefault(existingDefinintion, addingCriteria)
                    ?: parent?.actionForNoDefault(existingDefinintion, addingCriteria)

    private fun actionForNoDefault(existingDefinintion: Collection<BindingDefinition>, addingCriteria: BindingCriteria<*>): BindAction? =
            elements.firstAction(existingDefinintion, addingCriteria)
                    ?: defaultPolicyElement?.actionFor(existingDefinintion, addingCriteria)

    private fun List<PolicyElement>.firstAction(
            existingDefinintion: Collection<BindingDefinition>,
            addingCriteria: BindingCriteria<*>) = asSequence()
            .mapNotNull { it.actionFor(existingDefinintion, addingCriteria) }
            .firstOrNull()

}

