package neekle

import io.github.neyb.shoulk.matcher.match
import io.github.neyb.shoulk.shouldThrow
import io.github.neyb.shoulk.that
import org.junit.jupiter.api.Test
import test.StringWrapper

class InjectionNeekleTest {

    @Test fun `create a particle without a dependency fails`() {
        val neekle = Neekle {
            bind(particleType = singleton) { StringWrapper(it) }
        };

        { neekle<StringWrapper>() } shouldThrow CannotCreateParticle::class that match {
            it.definition == BindingDefinition(StringWrapper::class.java, null) && it.cause.let {
                it is CannotCreateParticle && it.definition == BindingDefinition(String::class.java, null) && it.cause.let {
                    it is NoParticleFound && it.definition == BindingDefinition(String::class.java, null)
                }
            }
        }
    }
}