package neekle.inject.spring

import neekle.inject.api.Locator
import org.springframework.context.ApplicationContext

class SpringLocator(private val context: ApplicationContext) : Locator {

    override fun <T> get(type: Class<T>, definition: String?): T =
            if (definition == null) context.getBean(type)
            else context.getBean(definition, type)

    @Suppress("UNCHECKED_CAST")
    override fun <T> getAll(type: Class<T>, definition: String?): Collection<T> = when {
        definition == null -> context.getBeansOfType(type).values
        definition.startsWith('@') -> context.getBeansWithAnnotation(Class.forName(definition.substringAfter('@')) as Class<Annotation>)
                .values
                .filter { type.isAssignableFrom(it.javaClass) }
                .map { it as T }
        else -> listOf(context.getBean(definition) as T)
    }


}


