package neekle

import neekle.BindAction.*
import neekle.inject.api.Injector

class Module(parentConflictPolicy: ConflictPolicy? = null) {
    private val conflictPolicy = ConflictPolicy(parentConflictPolicy)
    private val bindings = Bindings()
    private val subModules = Modules()

    inline fun <reified T> bind(particleType: ParticleType, name: String? = null, noinline init:(Injector) -> T) {
        bind(T::class.java, name) to particleType.createProvider(init)
    }

    inline fun <reified T> bind(name: String? = null): Binder<T> = bind(T::class.java, name)

    fun <T> bind(target: Class<T>, name: String? = null): Binder<T> {
        val criteria = BindingCriteria(target, name)
        val existingMatchingDefinition = getBindingsInConflict(criteria).map { it.definition }

        return if (existingMatchingDefinition.isEmpty()) Binder { add(target, it) }
        else binderForConflict(existingMatchingDefinition, criteria, target)
    }

    fun onAnyConflict(defaultAction: BindAction) {
        conflictPolicy.defaultPolicyElement = defaultAction.always
    }

    inline fun <reified T> onConflictOf(bindAction: BindAction) =
            onConflictOf(T::class.java, bindAction)

    fun <T> onConflictOf(type: Class<T>, bindAction: BindAction) {
        conflictPolicy.add(PolicyTypeElement(type) { bindAction })
    }

    private fun <T> binderForConflict(matchingDefinition: List<BindingDefinition>, criteria: BindingCriteria<T>, target: Class<T>): Binder<T> =
            when (conflictPolicy.actionFor(criteria.targetType) ?: throw BindingInConflict(criteria, matchingDefinition)) {
                ignore -> Binder {}
                add -> Binder { add(target, it) }
                replace -> Binder { bindings.replace(criteria, Binding(BindingDefinition.Assignable(target), it)) }
                fail -> throw BindingAlreadyPresent(criteria, matchingDefinition)
            }

    private fun <T> add(target: Class<T>, it: ParticleProvider<T>) {
        bindings.add(Binding(BindingDefinition.Assignable(target), it))
    }

    fun <T> getBindings(bindingCriteria: BindingCriteria<T>) =
            bindings.matching(bindingCriteria)

    fun <T> getBindingsInConflict(bindingCriteria: BindingCriteria<T>) =
            (conflictPolicy.getApplicablePolicyType(bindingCriteria.targetType)
                    ?.let { bindingCriteria.copy(targetType = it) } ?: bindingCriteria)
                    .let { bindings.matching(it) }
}

