package neekle

import neekle.BindAction.*
import neekle.inject.api.Injector

class Module(parentConflictPolicy: ConflictPolicy? = null) {
    private val conflictPolicy = ConflictPolicy(parentConflictPolicy)
    private val bindings = Bindings()
    private val subModules = Modules()

    inline fun <reified T> bind(
            particleType: ParticleType = singleton,
            name: String? = null,
            noinline init: (Injector) -> T) {
        bind(T::class.java, name, particleType.createProvider(init))
    }

    fun <T> bind(target: Class<T>, name: String? = null, provider: ParticleProvider<T>) {
        val criteria = BindingCriteria(target, name)
        val existingMatchingDefinition = getBindingsInConflict(criteria).map { it.definition }

        when (actionFor(criteria, existingMatchingDefinition)) {
            ignore -> Unit
            add -> add(target, provider)
            replace -> bindings.replace(criteria, Binding(BindingDefinition.Assignable(target), provider))
            fail -> throw BindingAlreadyPresent(criteria, existingMatchingDefinition)
        }
    }

    private fun <T> actionFor(
            criteria: BindingCriteria<T>,
            existingMatchingDefinition: List<BindingDefinition>) =
            if (existingMatchingDefinition.isEmpty()) add
            else getActionForConflict(criteria, existingMatchingDefinition)

    private fun <T> getActionForConflict(
            criteria: BindingCriteria<T>,
            existingMatchingDefinition: List<BindingDefinition>) =
            conflictPolicy.actionFor(criteria.targetType) ?: throw BindingInConflict(criteria, existingMatchingDefinition)

    fun onAnyConflict(defaultAction: BindAction) {
        conflictPolicy.defaultPolicyElement = defaultAction.always
    }

    inline fun <reified T> onConflictOf(bindAction: BindAction) =
            onConflictOf(T::class.java, bindAction)

    fun <T> onConflictOf(type: Class<T>, bindAction: BindAction) {
        conflictPolicy.add(PolicyTypeElement(type) { bindAction })
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

