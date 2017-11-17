package neekle

import io.github.neyb.shoulk.matcher.match
import io.github.neyb.shoulk.shouldBe
import io.github.neyb.shoulk.shouldMatchInOrder
import io.github.neyb.shoulk.shouldThrow
import org.junit.jupiter.api.Test
import test.NullableStringWrapper
import test.StringWrapper
import test.withCauses

class InjectionNeekleTest {

    @Test fun `create a component without a dependency fails`() {
        val neekle = Neekle {
            bind { StringWrapper(it) }
        }

        ({ neekle<StringWrapper>() } shouldThrow CannotCreateComponent::class).withCauses() shouldMatchInOrder listOf(
                match { it is CannotCreateComponent && it.message == "cannot create =>test.StringWrapper" },
                match { it is NoBindingFound && it.message == "no binding found for =>java.lang.String"})
    }

    @Test
    internal fun `can inject null`() {
        val neekle = Neekle {
            bind { NullableStringWrapper(it) }
            bind<String?> { null }
        }

        neekle<NullableStringWrapper>().value shouldBe null
    }
}