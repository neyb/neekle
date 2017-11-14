package neekle

import io.github.neyb.shoulk.shouldBe
import io.github.neyb.shoulk.shouldEqual
import org.junit.jupiter.api.Test
import test.StringWrapper

class SingletonNeekleTest {

    @Test fun `creating an empty neekle`() {
        Neekle {}
    }

    @Test fun `register and get singleton`() {
        val neekle = Neekle {
            bind<String>() to Singleton { "instance" }
        }

        neekle<String>() shouldEqual "instance"
    }

    @Test fun `get singleton several times should get same object`() {
        val neekle = Neekle {
            bind<String>() to Singleton { "instance" }
        }

        neekle<String>() shouldBe neekle()
    }

    @Test fun `singleton can be used in injection`() {

        val neekle = Neekle {
            bind<StringWrapper>() to Singleton { StringWrapper(it) }
            bind<String>() to "value"
        }

        neekle<StringWrapper>().value shouldEqual "value"
    }
}