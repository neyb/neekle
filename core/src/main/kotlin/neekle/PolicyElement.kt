package neekle


interface PolicyElement {

    companion object {
        operator fun invoke(policyElement: (Class<*>) -> BindAction?) = object : PolicyElement {
            override fun actionFor(addingtype: Class<*>) = policyElement(addingtype)
        }
    }

    fun actionFor(addingtype: Class<*>): BindAction?
}