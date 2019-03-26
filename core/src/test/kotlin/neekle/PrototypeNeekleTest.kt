package neekle

import io.github.neyb.shoulk.shouldEqual
import org.junit.jupiter.api.Test

class PrototypeNeekleTest {
    class A(val c:C)

    class B(val c:C)

    class C {
        companion object {
            var count = 0
        }

        init {
            count += 1
        }
    }

    @Test fun `prototype create a new instance at every injection`() {
        val neekle = Neekle {
            bind { A(inject()) }
            bind { B(inject()) }
            bind(bindingType = prototype) { C() }
        }

        C.count shouldEqual 0
        neekle<A>()
        C.count shouldEqual 1
        neekle<A>()
        C.count shouldEqual 1
        neekle<B>()
        C.count shouldEqual 2
    }
}