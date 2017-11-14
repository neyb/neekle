package neekle

import io.github.neyb.shoulk.hasMessage
import io.github.neyb.shoulk.shouldThrow
import io.github.neyb.shoulk.that
import org.junit.jupiter.api.Test
import test.StringWrapper

class InjectionNeekleTest {

    @Test fun `create a particle without a dependency fails`() {
        val neekle = Neekle {
            bind(singleton) { StringWrapper(it) }
        };

        { neekle<StringWrapper>() } shouldThrow NoParticleFound::class that hasMessage("azeaze")
    }
}