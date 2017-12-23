package neekle

import io.github.neyb.shoulk.shouldEqual
import neekle.inject.api.Injector
import org.junit.jupiter.api.Test

class LazyNeekleTest {


    class A(injector: Injector) {
        val b by lazy { injector<B>() }
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
            bind { A(it) }
        }

        val a = neekle<A>()
        B.count shouldEqual 0
        a.b
        B.count shouldEqual 1
    }
}