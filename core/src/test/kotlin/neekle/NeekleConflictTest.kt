package neekle

import io.github.neyb.shoulk.shouldEqual
import io.github.neyb.shoulk.shouldThrow
import org.junit.jupiter.api.Test

class NeekleConflictTest {

    @Test fun `registering several binding in conflict should fail`() {
        {
            Neekle {
                bind<String>() to Singleton { "instance1" }
                bind<String>() to Singleton { "instance2" }
            }
        } shouldThrow BindingInConflict::class
    }

    @Test fun `registering several binding in conflict should fail with fail policy`() {
        {
            Neekle {
                onAnyConflict(BindAction.fail)
                bind<String>() to Singleton { "instance1" }
                bind<String>() to Singleton { "instance2" }
            }
        } shouldThrow BindingAlreadyPresent::class
    }

    @Test fun `registering several string can be get as collection with add policy`() {
        val injector = Neekle {
            onAnyConflict(BindAction.add)
            bind<String>() to Singleton { "instance1" }
            bind<String>() to Singleton { "instance2" }
        }.injector

        { injector<String>() } shouldThrow SeveralParticlesFound::class
        injector.getAll<String>() shouldEqual listOf("instance1", "instance2")
    }

    @Test fun `registering several string can be get as collection with add policy on type`() {
        val injector = Neekle {
            onConflictOf<String>(BindAction.add)
            bind<String>() to Singleton { "instance1" }
            bind<String>() to Singleton { "instance2" }
        }.injector

        { injector<String>() } shouldThrow SeveralParticlesFound::class
        injector.getAll<String>() shouldEqual listOf("instance1", "instance2")
    }

    @Test fun `registering several charsequence fails with add policy on String`() {
        {
            Neekle {
                onConflictOf<String>(BindAction.add)
                bind<CharSequence>() to Singleton { "instance1" }
                bind<CharSequence>() to Singleton { "instance2" }
            }
        } shouldThrow BindingInConflict::class
    }

    @Test fun `registering several string fails with add policy on charsequence`() {
        val injector = Neekle {
            onConflictOf<CharSequence>(BindAction.add)
            bind<String>() to Singleton { "instance1" }
            bind<String>() to Singleton { "instance2" }
        }.injector

        { injector<CharSequence>() } shouldThrow SeveralParticlesFound::class
        injector.getAll<CharSequence>() shouldEqual listOf("instance1", "instance2")
    }

    @Test fun `cannot register several subclasses when declared conflict fail`() {
        {
            Neekle {
                onConflictOf<Any>(BindAction.fail)
                bind<String>() to Singleton { "value" }
                bind<Int>() to Singleton { 3 }
            }
        } shouldThrow BindingAlreadyPresent::class
    }

    @Test fun `ignore only keep the first`() {
        val neekle = Neekle {
            onConflictOf<String>(BindAction.ignore)
            bind<String>() to Singleton { "retained" }
            bind<String>() to Singleton { "ignored" }
            bind<String>() to Singleton { "ignored bis" }
        }

        neekle<String>() shouldEqual "retained"
    }

    @Test fun `replace only keep the last`() {
        val neekle = Neekle {
            onConflictOf<String>(BindAction.replace)
            bind<String>() to Singleton { "ignored" }
            bind<String>() to Singleton { "ignored bis" }
            bind<String>() to Singleton { "retained" }
        }

        neekle<String>() shouldEqual "retained"
    }
}