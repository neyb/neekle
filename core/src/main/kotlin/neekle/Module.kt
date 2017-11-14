package neekle

import neekle.BindAction.*
import neekle.inject.api.Injector

class Module internal constructor(parentConflictPolicy: ConflictPolicy? = null) {
    private val conflictPolicy = ConflictPolicy(parentConflictPolicy)
    private val bindings = Bindings()
    private val subModules = Modules()

    inline fun <reified T> bind(
            name: String? = null,
            particleType: ParticleType = singleton,
            noinline init: (Injector) -> T) =
        bind(T::class.java, name, particleType.createProvider(init))

    fun <T> bind(target: Class<T>, name: String? = null, provider: ParticleProvider<T>) {
        fun getConflictDefinitionOrNull(bindingDefinition: BindingDefinition<*>) =
                conflictPolicy.getApplicablePolicyType(bindingDefinition.type)
                        ?.let { bindingDefinition.copy(type = it) }

        fun getBindingsInConflict(bindingDefinition: BindingDefinition<*>) =
                (getConflictDefinitionOrNull(bindingDefinition) ?: bindingDefinition)
                        .let { bindings.inConflict(it) }

        fun <T> getActionForConflict(
                definition: BindingDefinition<T>,
                existingMatchingSpecification: List<BindingDefinition<*>>) =
                conflictPolicy.actionFor(definition.type) ?: throw BindingInConflict(definition, existingMatchingSpecification)

        fun <T> actionFor(
                definition: BindingDefinition<T>,
                existingMatchingDefinition: List<BindingDefinition<*>>) =
                if (existingMatchingDefinition.isEmpty()) add
                else getActionForConflict(definition, existingMatchingDefinition)

        val definition = BindingDefinition(target, name)
        val existingMatchingDefinition = getBindingsInConflict(definition).map { it.definition }

        when (actionFor(definition, existingMatchingDefinition)) {
            ignore -> Unit
            add -> bindings.add(Binding(definition, provider))
            replace -> bindings.replace(Binding(definition, provider))
            fail -> throw BindingAlreadyPresent(definition, existingMatchingDefinition)
        }
    }

    fun onAnyConflict(defaultAction: BindAction) {
        conflictPolicy.defaultPolicyElement = defaultAction.always
    }

    inline fun <reified T> onConflictOf(bindAction: BindAction) =
            onConflictOf(T::class.java, bindAction)

    fun <T> onConflictOf(type: Class<T>, bindAction: BindAction) {
        conflictPolicy.add(PolicyTypeElement(type) { bindAction })
    }

    internal fun <T> getBindings(bindingDefinition: BindingDefinition<T>) =
            bindings.matching(bindingDefinition)
}

class BindingAlreadyPresent(definition: BindingDefinition<*>, existingDefinitions: List<BindingDefinition<*>>)
    : Exception("$definition is in conflict with $existingDefinitions and policy is set to fail for it")

class BindingInConflict(definition: BindingDefinition<*>, definitionsInConflict: List<BindingDefinition<*>>)
    : Exception("$definition is in conflict with $definitionsInConflict, but no policy is present")