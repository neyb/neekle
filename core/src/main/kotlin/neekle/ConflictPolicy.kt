package neekle

class ConflictPolicy(private val parent: ConflictPolicy?) {
    private val watchedTypes = mutableListOf<Class<*>>()
    private val elements = mutableListOf<PolicyElement>()
    var defaultPolicyElement: PolicyElement? = null

    fun add(element: PolicyElement) {
        elements += element
        if (element is PolicyTypeElement<*>)
            watchedTypes += element.applyTo
    }

    fun actionFor(addingType: Class<*>) =
            actionForNoDefault(addingType) ?: parent?.actionForNoDefault(addingType)

    private fun actionForNoDefault(addingType: Class<*>): BindAction? =
            elements.firstAction(addingType)
                    ?: defaultPolicyElement?.actionFor(addingType)

    private fun List<PolicyElement>.firstAction(addingType: Class<*>) = asSequence()
            .mapNotNull { it.actionFor(addingType) }
            .firstOrNull()

    fun <T> getApplicablePolicyType(targetType: Class<out T>): Class<T>? =
            watchedTypes.filter { it.isAssignableFrom(targetType) }
                    .map { it as Class<T> }
                    .let { applicablePolicyTypes ->
                        when(applicablePolicyTypes.size) {
                            0 -> null
                            1 -> applicablePolicyTypes[0]
                            else -> throw SeveralApplicablePolicies(targetType)
                        }
                    }

}

