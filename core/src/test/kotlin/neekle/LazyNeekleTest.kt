package neekle

import io.github.neyb.shoulk.shouldEqual
import org.junit.jupiter.api.Test

class LazyNeekleTest {

    class A(val lazyB: () -> B) {
        val b by lazy(lazyB)
    }

    class B {
        companion object {
            var count = 0;
        }

        init {
            count += 1
        }
    }

    @Test fun `injecting lazyly`() {

        val neekle = Neekle {
            bind { B() }
            bind { A(lazy()) }
        }

        val a = neekle<A>()
        B.count shouldEqual 0
        a.b
        B.count shouldEqual 1
    }
}