package neekle.inject.spring

import io.github.neyb.shoulk.matcher.match
import io.github.neyb.shoulk.shouldMatchInAnyOrder
import io.github.neyb.shoulk.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class SpringLocatorTest {

    @Test fun `load a context`() {
        val context = AnnotationConfigApplicationContext("neekle.inject.spring")

        val componentA = context.getBean(MyComponentA::class.java)

        componentA.componentB shouldNotBe null
        componentA.myComponents shouldMatchInAnyOrder listOf(
                match("is a ComponentB") { it is MyComponentB },
                match("is a ComponentC") { it is MyComponentC })
        componentA.annotatedComponents shouldMatchInAnyOrder listOf(
                match("is a ComponentC") { it is MyComponentC })
    }
}