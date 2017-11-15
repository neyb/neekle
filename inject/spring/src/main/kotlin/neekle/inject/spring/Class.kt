@file:Suppress("UNCHECKED_CAST")

package neekle.inject.spring

fun <N> Any?.asOrNull(target: Class<N>): N? = if (target.isInstance(this)) this as N else null

fun <N> Class<*>.asOrNull(target: Class<N>) = takeIf { target.isAssignableFrom(it) }?.let { it as Class<out N> }
