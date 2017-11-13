package neekle.inject.spring

import neekle.inject.spring.SpringDefinition.ByAnnotation
import neekle.inject.spring.SpringDefinition.ByName
import org.springframework.context.ApplicationContext

internal fun <T> String.toSpringDefinition(target:Class<T>) =
        if (!startsWith('@')) ByName(this, target)
        else ByAnnotation(toAnnotation(), target)

private fun String.toAnnotation(): Class<Annotation> = (Class.forName(substringAfter('@'))
        .takeIf { Annotation::class.java.isAssignableFrom(it) }
        ?: throw IllegalArgumentException("${substringAfter('@')} is not an annotation"))
        as Class<Annotation>


internal sealed class SpringDefinition<T>(protected val targetType: Class<T>) {

    abstract operator fun get(context: ApplicationContext): Collection<T>

    class ByName<T>(private val name: String, targetType: Class<T>) : SpringDefinition<T>(targetType) {
        override fun get(context: ApplicationContext) = listOf(context.getBean(name, targetType))
    }

    class ByAnnotation<T>(private val annotation: Class<Annotation>, targetType: Class<T>) : SpringDefinition<T>(targetType) {
        override fun get(context: ApplicationContext) = context.getBeansWithAnnotation(annotation)
                .values
                .filter { targetType.isInstance(it) }
                .map { it as T }

    }
}