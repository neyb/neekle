package samples

import io.github.neyb.shoulk.shouldEqual
import neekle.Configuration
import neekle.Neekle
import org.junit.jupiter.api.Test

class UsingDefaultConfigurationTest {

    @Test fun `default configuration can be overriden`() {
        val neekle = Neekle {
            bind { A("my A", inject()) }
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

            bind { A("my A", inject()) }
            bind { C("my C") }
        }

        val a = neekle<A>()

        a.name shouldEqual "my A"
        a.b.name shouldEqual "default B"
        a.b.c.name shouldEqual "my C"
    }
}


private class A(val name: String, val b: B)

private class B(val name: String, val c: C)

private class C(val name: String)

private val defaultConfiguration: Configuration = {
    bindDefault { A("default A", inject()) }
    bindDefault { B("default B", inject()) }
    bindDefault { C("default C") }
}