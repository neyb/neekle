package neekle.inject.spring.inject.api

import io.github.neyb.shoulk.shouldEqual
import org.junit.jupiter.api.Test

@Suppress("MemberVisibilityCanPrivate", "UNCHECKED_CAST")
class InjectorTest {
    class TestLocator : neekle.inject.api.Locator {
        private val particles = mapOf<Class<*>, Any>(
                String::class.java to "particle")

        override fun <T> get(type: Class<T>, definition: String?) = particles[type] as T

        override fun <T> getAll(type: Class<T>, definition: String?) = listOf(particles[type] as T)
    }

    val inject = neekle.inject.api.Injector(TestLocator())

    @Test fun `can create an inject`() {
    }

    @Test fun `can access an object from Inject`() {
        inject<String>() shouldEqual "particle"
    }

    @Test fun `can access several items in one time`() {
        val test: Collection<String> = inject.getAll()
        test shouldEqual listOf("particle")
    }
}