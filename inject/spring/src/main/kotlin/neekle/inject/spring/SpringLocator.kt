package neekle.inject.spring

import neekle.inject.api.Locator
import org.springframework.context.ApplicationContext

class SpringLocator(private val context: ApplicationContext) : Locator {

    override fun <T> get(type: Class<T>, name: String?): T =
            if (name == null) context.getBean(type)
            else context.getBean(name, type)

    @Suppress("UNCHECKED_CAST")
    override fun <T> getAll(type: Class<T>, name: String?): Collection<T> = when {
        name == null -> context.getBeansOfType(type).values
        name.startsWith('@') ->
            (Class.forName(name.substringAfter('@')).asOrNull(Annotation::class.java)
                    ?: throw IllegalArgumentException(name.substringAfter('@') + "is not an annotation"))
                    .let { context.getBeansWithAnnotation(it) }.values
                    .filter { type.isAssignableFrom(it.javaClass) }
                    .map { it as T }
        else -> listOf(context.getBean(name) as T)
    }


}


