package neekle

import neekle.BindAction.*
import neekle.ModuleConfigurer.Companion.configure

internal class Module internal constructor(
        parentConflictPolicy: ConflictPolicy? = null,
        private val bindings: Bindings = Bindings()
                                          ) : ConfigurableModule, BindingsFinder {
    private val conflictPolicy = ConflictPolicy(parentConflictPolicy)

    private val subModules = Modules()

    override fun <T> bind(target: Class<T>, name: String?, provider: ParticleProvider<T>) {
        val definition = BindingDefinition(target, name)
        val definitionsInConflict = getBindingsInConflict(definition).map { it.definition }

        when (actionFor(definition, definitionsInConflict)) {
            ignore -> Unit
            add -> bindings.add(Binding(definition, provider))
            replace -> bindings.replace(Binding(definition, provider))
            fail -> throw BindingAlreadyPresent(definition, definitionsInConflict)
        }
    }

    private fun getConflictDefinitionOrNull(bindingDefinition: BindingDefinition<*>) =
            conflictPolicy.getApplicablePolicyType(bindingDefinition.type)
                    ?.let { bindingDefinition.copy(type = it) }

    private fun getBindingsInConflict(bindingDefinition: BindingDefinition<*>) =
            (getConflictDefinitionOrNull(bindingDefinition) ?: bindingDefinition)
                    .let { bindings.inConflict(it) }

    private fun <T> getActionForConflict(
            definition: BindingDefinition<T>,
            existingMatchingSpecification: List<BindingDefinition<*>>) =
            conflictPolicy.actionFor(definition.type) ?: throw BindingInConflict(definition, existingMatchingSpecification)

    private fun <T> actionFor(
            definition: BindingDefinition<T>,
            existingMatchingDefinition: List<BindingDefinition<*>>) =
            if (existingMatchingDefinition.isEmpty()) add
            else getActionForConflict(definition, existingMatchingDefinition)

    override fun onAnyConflict(defaultAction: BindAction) {
        conflictPolicy.defaultPolicyElement = defaultAction.always
    }

    override fun <T> onConflict(type: Class<T>, bindAction: BindAction) {
        conflictPolicy.add(PolicyTypeElement(type) { bindAction })
    }

    override fun submodule(configuration: Configuration) {
        subModules.add(configuration.configure(Module(conflictPolicy, bindings)))
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