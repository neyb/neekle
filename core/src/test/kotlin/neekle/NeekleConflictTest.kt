package neekle

import io.github.neyb.shoulk.shouldEqual
import io.github.neyb.shoulk.shouldThrow
import org.junit.jupiter.api.Test

class NeekleConflictTest {

    @Test fun `registering several binding in conflict should fail`() {
        {
            Neekle {
                bind<String>() with Singleton { "instance1" }
                bind<String>() with Singleton { "instance2" }
            }
        } shouldThrow BindingInConflict::class
    }

    @Test fun `registering several string can be get as collection`() {
        val injector = Neekle {
            bind<String>() with Singleton { "instance1" }
            bind<String>() with Singleton { "instance2" }
        }.injector

        injector.getAll<String>() shouldEqual listOf("instance1", "instance2")
    }
}