package neekle

import io.github.neyb.shoulk.matcher.match
import io.github.neyb.shoulk.shouldBe
import io.github.neyb.shoulk.shouldMatchInOrder
import io.github.neyb.shoulk.shouldNotBe
import io.github.neyb.shoulk.shouldThrow
import org.junit.jupiter.api.Test
import test.*

class BasicNeekleTest {

    @Test fun `binding a type and getting it`() {
        val neekle = Neekle {
            bind { A() }
        }

        neekle<A>() shouldNotBe null
    }

    @Test fun `create components with dependencies`() {
        val neekle = Neekle {
            bind { A() }
            bind { B(inject()) }
        }

        neekle<A>() shouldNotBe null
    }

    @Test
    internal fun `of course the order of declaration does not matter (this is what we all inversion of control)`() {
        val neekle = Neekle {
            bind { B(inject()) }
            bind { A() }
        }

        neekle<A>() shouldNotBe null
    }

    @Test fun `create a component without a dependency fails`() {
        val neekle = Neekle {
            bind { StringWrapper(inject()) }
        }

        ({ neekle<StringWrapper>() } shouldThrow CannotCreateComponent::class).withCauses() shouldMatchInOrder listOf(
                match { it is CannotCreateComponent && it.message == "cannot create =>test.StringWrapper" },
                match { it is NoComponentFound && it.message == "no component found for =>java.lang.String" })
    }

    @Test fun `create a component with several dependencies fails`() {
        val neekle = Neekle {
            bind { StringWrapper(inject()) }
            bind { "a string" }
            bind { "another string" }
        }

        //TODO should fail faster
        ({ neekle<StringWrapper>() } shouldThrow CannotCreateComponent::class).withCauses() shouldMatchInOrder listOf(
                match { it is CannotCreateComponent && it.message == "cannot create =>test.StringWrapper" },
                match { it is SeveralComponentsFound && it.message!!.startsWith("2 components found for =>java.lang.String:") })
    }

    @Test fun `can inject null`() {
        val neekle = Neekle {
            bind { NullableStringWrapper(inject()) }
            bind<String?> { null }
        }

        neekle<NullableStringWrapper>().value shouldBe null
    }
}