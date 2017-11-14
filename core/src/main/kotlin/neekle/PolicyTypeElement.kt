package neekle

internal class PolicyTypeElement<T>(
        val applyTo : Class<T>,
        private val policyElement: (Class<*>) -> BindAction?
) : PolicyElement {
    override fun actionFor(addingtype: Class<*>) =
            policyElement(addingtype).takeIf { applyTo.isAssignableFrom(addingtype) }
}