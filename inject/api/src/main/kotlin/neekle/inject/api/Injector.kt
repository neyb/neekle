package neekle.inject.api

class Injector (private val locator: neekle.inject.api.Locator) {
    operator inline fun <reified T> invoke(definition: String? = null) = get(T::class.java, definition)
    inline fun <reified T> getAll(definition: String? = null) = getAll(T::class.java, definition)

    operator fun <T> get(type: Class<T>, definition: String? = null) = locator[type, definition]
    fun <T> getAll(type: Class<T>, definition: String? = null) = locator.getAll(type, definition)
}

