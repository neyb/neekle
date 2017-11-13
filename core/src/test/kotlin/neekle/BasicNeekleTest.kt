package neekle

import io.github.neyb.shoulk.shouldBe
import io.github.neyb.shoulk.shouldEqual
import org.junit.jupiter.api.Test

class BasicNeekleTest {
    @Test fun `creating an empty neekle`() {
        Neekle {}
    }

    @Test fun `register and get singleton`() {
        val neekle = Neekle {
            bind<String>() with Singleton { "instance" }
        }

        neekle.injector<String>() shouldEqual "instance"
    }

    @Test fun `get singleton several times should get same object`() {
        val injector = Neekle {
            bind<String>() with Singleton { "instance" }
        }.injector

        injector<String>() shouldBe injector()
    }
}