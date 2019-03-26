package neekle

import io.github.neyb.shoulk.shouldBe
import io.github.neyb.shoulk.shouldEqual
import org.junit.jupiter.api.Test
import test.StringWrapper

class SingletonProviderNeekleTest {

    @Test fun `creating an empty neekle`() {
        Neekle {}
    }

    @Test fun `register and get singleton`() {
        val neekle = Neekle {
            bind { "instance" }
        }

        neekle<String>() shouldEqual "instance"
    }

    @Test fun `get singleton several times should get same object`() {
        val neekle = Neekle {
            bind { "instance" }
        }

        neekle<String>() shouldBe neekle()
    }

    @Test fun `singleton can be used in injection`() {

        val neekle = Neekle {
            bind { StringWrapper(inject()) }
            bind { "value" }
        }

        neekle<StringWrapper>().value shouldEqual "value"
    }
}