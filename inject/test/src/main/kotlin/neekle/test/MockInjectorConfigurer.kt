package neekle.test

class MockInjectorConfigurer internal constructor(
        private val mockLocator: MockLocator) {
    inline fun <reified T> with(instance: T) = with(T::class.java, instance)

    fun <T> with(type: Class<T>, instance: T) {
        mockLocator.add(type, instance)
    }

}