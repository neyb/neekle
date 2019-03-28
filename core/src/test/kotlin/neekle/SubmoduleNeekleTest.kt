package neekle

import io.github.neyb.shoulk.matcher.match
import io.github.neyb.shoulk.shouldEqual
import io.github.neyb.shoulk.shouldMatchInOrder
import io.github.neyb.shoulk.shouldNotBe
import io.github.neyb.shoulk.shouldThrow
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import test.A
import test.B
import test.C
import test.withCauses

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
                submodule {
                    bind { A() }
                }
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

    @Test fun `internal component cannot be accessed from a parent`() {
        val neekle = Neekle {
            submodule {
                bindInternal { A() }
            }
            bind { B(inject()) }
        };

        ({ neekle<B>() } shouldThrow CannotCreateComponent::class)
                .withCauses() shouldMatchInOrder listOf(
                match { it is CannotCreateComponent && it.message == "cannot create =>test.B" },
                match { it is NoComponentFound && it.message == "no component found for =>test.A" })
    }

    @Test fun `internal component can be accessed from submodule`() {
        val neekle = Neekle {
            submodule {
                bindInternal { A() }
                submodule {
                    bind { B(inject()) }
                }
            }
        }

        neekle<B>() shouldNotBe null
    }

    @Test
    internal fun `internal component at top level cannot be accessed from outside`() {
        val neekle = Neekle {
            bindInternal { A() }
        };

        { neekle<A>() } shouldThrow NoComponentFound::class
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