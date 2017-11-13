package neekle

import io.github.neyb.shoulk.shouldEqual
import org.junit.jupiter.api.Test

class DuplicateParticleNeekleTest {
    @Test fun `registering several string can be get as collection`() {
        val injector = Neekle {
            bind<String>() with Singleton { "instance1" }
            bind<String>() with Singleton { "instance2" }
        }.injector

        injector.getAll<String>() shouldEqual listOf("instance1", "instance2")
    }
}