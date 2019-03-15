package neekle

import neekle.inject.api.Locator

internal class Registry : BindingsFinder {
    private val bindings = mutableListOf<Binding<*>>()
    private val locators = mutableListOf<Locator>()

    fun <T> add(binding: Binding<T>) {
        bindings.add(binding)
    }

    fun add(locator: Locator) {
        locators += locator
    }

    override fun <T> getBindings(definition: BindingDefinition<T>): List<Binding<T>> {
        return bindings.mapNotNull { it.asCandidateForOrNull(definition) }
    }


}