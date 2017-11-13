package neekle

sealed class BindingDefinition {
    abstract fun match(criteria: BindingCriteria<*>): Boolean

    class Assignable<in T>(private val registered: Class<T>) : BindingDefinition() {
        override fun match(criteria: BindingCriteria<*>) = criteria.targetType.isAssignableFrom(registered)
    }


}

