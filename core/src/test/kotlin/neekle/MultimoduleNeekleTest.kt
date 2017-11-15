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

    @Test fun `policy define in parent propagate to submodule`() {
        Neekle {
            onAnyConflict(BindAction.ignore)
            submodule {
                bind { "value1" }
                bind { "value2" }
            }
        }<String>() shouldEqual "value1"
    }

    @Test fun `policy define in submodule does not apply to parent`() {
        {
            Neekle {
                submodule {
                    onAnyConflict(BindAction.ignore)
                }
                bind { "value1" }
                bind { "value2" }
            }
        } shouldThrow BindingInConflict::class
    }

    @Test fun `specific parent policy is prioritary to default submodule even in submodule`() {
        Neekle {
            onConflict<String>(BindAction.ignore)
            submodule {
                onAnyConflict(BindAction.fail)
                bind { "value1" }
                bind { "value2" }
            }
        }
    }
}