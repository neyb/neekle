package neekle.inject.spring

import org.springframework.context.ApplicationContext

internal fun <T : Any> String.toSpringDefinition(target: Class<T>) =
        if (!startsWith('@')) SpringDefinition.ByName(this, target)
        else SpringDefinition.ByAnnotation(toAnnotation(), target)

private fun String.toAnnotation() = (Class.forName(substringAfter('@')).asOrNull(Annotation::class.java)
        ?: throw IllegalArgumentException("${substringAfter('@')} is not an annotation"))

//TODO this need improvement
internal sealed class SpringDefinition<T : Any>(protected val targetType: Class<T>) {

    abstract operator fun get(context: ApplicationContext): Collection<T>

    class ByName<T : Any>(private val name: String, targetType: Class<T>) : SpringDefinition<T>(targetType) {
        override fun get(context: ApplicationContext) = listOf(context.getBean(name, targetType))
    }

    class ByAnnotation<T : Any>(
            private val annotation: Class<out Annotation>,
            targetType: Class<T>) : SpringDefinition<T>(targetType) {
        override fun get(context: ApplicationContext): List<T> =
                context.getBeansWithAnnotation(annotation).values
                        .mapNotNull<Any, T> { it.asOrNull(targetType) }

    }
}

