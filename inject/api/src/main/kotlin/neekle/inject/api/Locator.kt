package neekle.inject.api

interface Locator {
    operator fun <T> get(type: Class<T>, name: String? = null): T
    fun <T> getAll(type: Class<T>, name: String?): Collection<T>
}
