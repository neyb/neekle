package neekle

import io.github.neyb.shoulk.matcher.match
import io.github.neyb.shoulk.shouldMatchInOrder
import io.github.neyb.shoulk.shouldThrow
import org.junit.jupiter.api.Test
import test.StringWrapper
import test.withCauses

class InjectionNeekleTest {

    @Test fun `create a particle without a dependency fails`() {
        val neekle = Neekle {
            bind(particleType = singleton) { StringWrapper(it) }
        }

        ({ neekle<StringWrapper>() } shouldThrow CannotCreateParticle::class)
                .withCauses() shouldMatchInOrder listOf(
                match { it is CannotCreateParticle && it.definition == BindingDefinition(StringWrapper::class.java, null) },
                match { it is CannotCreateParticle && it.definition == BindingDefinition(String::class.java, null) },
                match { it is NoParticleFound && it.definition == BindingDefinition(String::class.java, null) })
    }
}