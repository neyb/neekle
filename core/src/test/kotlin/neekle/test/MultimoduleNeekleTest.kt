package neekle.test

import io.github.neyb.shoulk.shouldEqual
import neekle.Neekle
import org.junit.jupiter.api.Test

class MultimoduleNeekleTest {

    @Test fun `binding a particle in a submodule get be used`() {
        val neekle = Neekle {
            submodule {
                bind { "value" }
            }
        }

        neekle<String>() shouldEqual "value"

    }
}