package neekle.inject.spring.inject.api

import io.github.neyb.shoulk.shouldEqual
import org.junit.jupiter.api.Test

@Suppress("MemberVisibilityCanPrivate", "UNCHECKED_CAST")
class InjectorTest {
    class TestLocator : neekle.inject.api.Locator {
        private val components = mapOf<Class<*>, Any>(
                String::class.java to "component")

        override fun <T> get(type: Class<T>, name: String?) = components[type] as T

        override fun <T> getAll(type: Class<T>, name: String?) = listOf(components[type] as T)
    }

    val inject = neekle.inject.api.Injector(TestLocator())

    @Test fun `can create an inject`() {
    }

    @Test fun `can access an object from Inject`() {
        inject<String>() shouldEqual "component"
    }

    @Test fun `can access several items in one time`() {
        val test: Collection<String> = inject.getAll()
        test shouldEqual listOf("component")
    }
}