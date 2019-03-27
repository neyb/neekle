package neekle

import io.github.neyb.shoulk.shouldEqual
import org.junit.jupiter.api.Test

class LazyNeekleTest {

    class A {
        companion object {
            var count = 0;
        }

        init {
            count += 1
        }
    }

    class B(lazyA:() -> A) {
        val a by lazy(lazyA)
    }

    @Test fun `injecting lazyly`() {

        val neekle = Neekle {
            bind { B(lazy()) }
            bind { A() }
        }

        val b = neekle<B>()
        A.count shouldEqual 0
        b.a
        A.count shouldEqual 1
    }
}