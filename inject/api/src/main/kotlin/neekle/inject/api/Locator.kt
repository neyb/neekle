package neekle.inject.api

interface Locator {
    operator fun <T> get(type: Class<T>, definition: String? = null): T
    fun <T> getAll(type: Class<T>, definition: String?): Collection<T>
}
