package neekle.test

import neekle.inject.api.Locator

class MockLocator(
        private val mocker: Mocker,
        configuration: MockInjectorConfigurer.() -> Unit) : Locator {
    private val instances = mutableMapOf<Class<*>, MutableList<Any?>>()

    init {
        configuration(MockInjectorConfigurer(this))
    }

    override fun <T> get(type: Class<T>, name: String?) = getOrMock(type).single()

    override fun <T> getAll(type: Class<T>, name: String?): Collection<T> = getOrMock(type)

    private fun <T> getOrMock(type: Class<T>): Collection<T> {
        return getExisting(type).apply {
            if (isEmpty()) add(mocker.mock(type))
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getExisting(type: Class<T>): MutableCollection<T> {
        return instances.computeIfAbsent(type) { mutableListOf() } as MutableCollection<T>
//        instances.asSequence()
//                .filter { (k, _) -> type.isAssignableFrom(k) }
//                .map { it.value as Collection<T> }
//                .flatten()
//                .toMutableList()
    }

    internal fun <T> add(type: Class<T>, instance: T) {
        instances.computeIfAbsent(type) { mutableListOf() }
                .add(instance)
    }
}