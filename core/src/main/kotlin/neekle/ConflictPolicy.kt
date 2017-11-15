package neekle

internal class ConflictPolicy(private val parent: ConflictPolicy?) {
    private val watchedTypes = mutableListOf<Class<*>>()
    private val elements = mutableListOf<PolicyElement>()
    var defaultPolicyElement: PolicyElement? = null

    fun add(element: PolicyElement) {
        elements += element
        if (element is PolicyTypeElement<*>)
            watchedTypes += element.applyTo
    }

    fun actionFor(addingType: Class<*>) =
            ActionForNoDefault(addingType)?: defaultActionFor(addingType)

    private fun ActionForNoDefault(addingType: Class<*>) = (selfActionForNoDefault(addingType)
            ?: parent?.selfActionForNoDefault(addingType))

    fun getApplicablePolicyType(targetType: Class<*>): Class<*>? =
            watchedTypes.filter { it.isAssignableFrom(targetType) }
                    .let { applicablePolicyTypes ->
                        when (applicablePolicyTypes.size) {
                            0 -> null
                            1 -> applicablePolicyTypes[0]
                            else -> throw SeveralApplicablePolicies(targetType)
                        }
                    }

    private fun selfActionForNoDefault(addingType: Class<*>): BindAction? = elements.firstAction(addingType)

    private fun defaultActionFor(addingType: Class<*>) = selfDefaultActionFor(addingType)
            ?: parent?.selfDefaultActionFor(addingType)

    private fun selfDefaultActionFor(addingType: Class<*>) = defaultPolicyElement?.actionFor(addingType)

    private fun List<PolicyElement>.firstAction(addingType: Class<*>) = asSequence()
            .mapNotNull { it.actionFor(addingType) }
            .firstOrNull()
}

class SeveralApplicablePolicies(type: Class<*>) : Exception("several policies found for type $type")

