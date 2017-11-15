package neekle.test

import io.github.neyb.shoulk.matcher.match
import io.github.neyb.shoulk.shouldMatchInOrder
import io.github.neyb.shoulk.shouldThrow
import neekle.CannotCreateParticle
import neekle.Neekle
import neekle.NoParticleFound
import org.junit.jupiter.api.Test
import test.StringWrapper
import test.withCauses

class InjectionNeekleTest {

    @Test fun `create a particle without a dependency fails`() {
        val neekle = Neekle {
            bind(particleType = singleton) { StringWrapper(it) }
        }

        ({ neekle<StringWrapper>() } shouldThrow CannotCreateParticle::class).withCauses() shouldMatchInOrder listOf(
                match { it is CannotCreateParticle && it.message == "cannot create test.StringWrapper" },
                match { it is CannotCreateParticle && it.message == "cannot create java.lang.String"},
                match { it is NoParticleFound && it.message == "no particle found for java.lang.String"})
    }
}