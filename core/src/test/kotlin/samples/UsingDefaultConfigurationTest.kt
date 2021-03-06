package samples

import io.github.neyb.shoulk.shouldEqual
import neekle.BindAction
import neekle.Configuration
import neekle.Neekle
import neekle.inject.api.Injector
import org.junit.jupiter.api.Test

class UsingDefaultConfigurationTest {
    @Test fun `default configuration can be overriden`() {
        val neekle = Neekle {
            bind { A("my A", it) }
            bind { C("my C") }
            submodule(defaultConfiguration)
        }

        val a = neekle<A>()

        a.name shouldEqual "my A"
        a.b.name shouldEqual "default B"
        a.b.c.name shouldEqual "my C"
    }


    @Test fun `when using defaultModule, custom bindings can be done after`() {
        val neekle = Neekle {
            defaultModule(defaultConfiguration)

            bind { A("my A", it) }
            bind { C("my C") }
        }

        val a = neekle<A>()

        a.name shouldEqual "my A"
        a.b.name shouldEqual "default B"
        a.b.c.name shouldEqual "my C"
    }
}


class A(val name: String, injector: Injector) {
    val b: B = injector()
}

class B(val name: String, injector: Injector) {
    val c: C = injector()
}

class C(val name: String)

val defaultConfiguration: Configuration = {
    onAnyConflict(BindAction.ignore)

    bind { A("default A", it) }
    bind { B("default B", it) }
    bind { C("default C") }
}