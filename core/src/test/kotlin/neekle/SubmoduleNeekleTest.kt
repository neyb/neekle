package neekle

import io.github.neyb.shoulk.shouldEqual
import io.github.neyb.shoulk.shouldNotBe
import org.junit.jupiter.api.Test
import test.A
import test.B

class SubmoduleNeekleTest {

    @Test fun `binding a component in a submodule get be used`() {
        val neekle = Neekle {
            submodule {
                bind { "value" }
            }
        }

        neekle<String>() shouldEqual "value"
    }

    @Test fun `a component can find its dependency in a submodule`() {
        val neekle = Neekle {
            submodule {
                bind { A() }
            }
            bind { B(inject()) }
        }

        neekle<B>() shouldNotBe null
    }

    @Test fun `a component can find its dependency in a supermodule`() {
        val neekle = Neekle {
            submodule {
                submodule {
                    bind { B(inject()) }
                }
            }
            bind { A() }
        }

        neekle<B>() shouldNotBe null
    }

    //    @Test fun `conflict can occure with a submodule`() {
    //        {
    //            Neekle {
    //                bind { "value1" }
    //                submodule {
    //                    bind { "value2" }
    //                }
    //            }
    //        } shouldThrow BindingInConflict::class
    //    }

    //    @Test fun `policy define in parent propagate to submodule`() {
    //        Neekle {
    //            onAnyConflict(BindAction.ignore)
    //            submodule {
    //                bind { "value1" }
    //                bind { "value2" }
    //            }
    //        }<String>() shouldEqual "value1"
    //    }

    //    @Test fun `policy define in submodule does not apply to parent`() {
    //        {
    //            Neekle {
    //                submodule {
    //                    onAnyConflict(BindAction.ignore)
    //                }
    //                bind { "value1" }
    //                bind { "value2" }
    //            }
    //        } shouldThrow BindingInConflict::class
    //    }

    //    @Test @Disabled fun `specific parent policy is prioritary to default submodule even in submodule`() {
    //        Neekle {
    //            onConflict<String>(BindAction.ignore)
    //            submodule {
    //                onAnyConflict(BindAction.fail)
    //                bind { "value1" }
    //                bind { "value2" }
    //            }
    //        }
    //    }
}