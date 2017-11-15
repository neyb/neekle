package neekle

import neekle.BindAction.*

internal class Module internal constructor(
        parentConflictPolicy: ConflictPolicy? = null,
        private val bindings: Bindings = Bindings()
                                          ) : ConfigurableModule, BindingsFinder {
    private val conflictPolicy = ConflictPolicy(parentConflictPolicy)

    private val subModules = Modules()

    override fun <T> bind(target: Class<T>, name: String?, provider: ParticleProvider<T>) {
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

    override fun onAnyConflict(defaultAction: BindAction) {
        conflictPolicy.defaultPolicyElement = defaultAction.always
    }

    override fun <T> onConflict(type: Class<T>, bindAction: BindAction) {
        conflictPolicy.add(PolicyTypeElement(type) { bindAction })
    }

    override fun submodule(init: ModuleConfigurer.() -> Unit) {
        subModules.add(Module(conflictPolicy, bindings).also { ModuleConfigurer(it).apply(init) })
    }

    override fun <T> getBindings(definition: BindingDefinition<T>) = bindings.matching(definition)
}

class BindingAlreadyPresent internal constructor(
        definition: BindingDefinition<*>,
        existingDefinitions: List<BindingDefinition<*>>)
    : Exception("$definition is in conflict with $existingDefinitions and policy is set to fail for it")

class BindingInConflict internal constructor(
        definition: BindingDefinition<*>,
        definitionsInConflict: List<BindingDefinition<*>>)
    : Exception("$definition is in conflict with $definitionsInConflict, but no policy is present")