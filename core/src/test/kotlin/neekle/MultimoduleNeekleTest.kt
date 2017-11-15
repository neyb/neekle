package neekle

import io.github.neyb.shoulk.shouldEqual
import io.github.neyb.shoulk.shouldThrow
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

    @Test fun `conflict can occure with a submodule`() {
        {
            Neekle {
                bind { "value1" }
                submodule {
                    bind { "value2" }
                }
            }
        } shouldThrow BindingInConflict::class
    }
}