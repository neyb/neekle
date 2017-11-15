package samples

import io.github.neyb.shoulk.shouldEqual
import neekle.BindAction
import neekle.ModuleConfigurer
import neekle.Neekle
import neekle.inject.api.Injector
import org.junit.jupiter.api.Test

class UsingDefaultConfigurationTest {
    @Test fun `load context`() {
        val neekle = Neekle {
            bind { A("my A", it) }
            bind { C("my C") }
            submodule(DefaultConfiguration)
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

object DefaultConfiguration : (ModuleConfigurer) -> Unit {
    override fun invoke(configurer: ModuleConfigurer) {
        configurer.apply {
            onAnyConflict(BindAction.ignore)

            bind { A("default A", it) }
            bind { B("default B", it) }
            bind { C("default C") }
        }
    }
}