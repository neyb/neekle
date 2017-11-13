package neekle

import neekle.BindAction.*

class Module(parentConflictPolicy: ConflictPolicy? = null) {

    private val conflictPolicy = ConflictPolicy(parentConflictPolicy)
    private val bindings = Bindings()
    private val subModules = Modules()

    inline fun <reified T> bind(name: String? = null): Binder<T> = bind(T::class.java, name)

    fun <T> bind(target: Class<T>, name: String? = null): Binder<T> {
        val criteria = BindingCriteria(target, name)
        val matchingDefinition = getBindings(criteria).map { it.definition }
        return if (matchingDefinition.isEmpty()) Binder { add(target, it) }
        else binderForConflict(matchingDefinition, criteria, target)
    }

    private fun <T> binderForConflict(matchingDefinition: List<BindingDefinition>, criteria: BindingCriteria<T>, target: Class<T>): Binder<T> {
        return when (conflictPolicy.actionFor(matchingDefinition, criteria)
            ?: throw BindingInConflict(criteria, matchingDefinition)
        ) {
            ignore -> Binder {}
            add -> Binder { add(target, it) }
            replace -> Binder { bindings.replace(criteria, Binding(BindingDefinition.Assignable(target), it)) }
            fail -> throw BindingAlreadyPresent(criteria, matchingDefinition)
        }
    }

    private fun <T> add(target: Class<T>, it: ParticleProvider<T>) {
        bindings.add(Binding(BindingDefinition.Assignable(target), it))
    }

    fun <T> getBindings(bindingCriteria: BindingCriteria<T>) =
            bindings.matching(bindingCriteria)
}

