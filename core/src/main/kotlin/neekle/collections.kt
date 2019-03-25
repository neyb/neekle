package neekle

internal fun <T> Collection<T>.single(defaultProvider: () -> T, severalFilter: (Collection<T>) -> T): T = when (size) {
    0 -> defaultProvider()
    1 -> first()
    else -> severalFilter(this)
}
