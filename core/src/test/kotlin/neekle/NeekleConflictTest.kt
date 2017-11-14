package neekle

import io.github.neyb.shoulk.matcher.match
import io.github.neyb.shoulk.shouldEqual
import io.github.neyb.shoulk.shouldThrow
import io.github.neyb.shoulk.that
import org.junit.jupiter.api.Test

class NeekleConflictTest {

    @Test fun `registering several binding in conflict should fail`() {
        {
            Neekle {
                bind { "instance1" }
                bind { "instance2" }
            }
        } shouldThrow BindingInConflict::class
    }

    @Test fun `registering several binding in conflict should fail with fail policy`() {
        {
            Neekle {
                onAnyConflict(BindAction.fail)
                bind { "instance1" }
                bind { "instance2" }
            }
        } shouldThrow BindingAlreadyPresent::class
    }

    @Test fun `registering several string can be get as collection with add policy`() {
        val injector = Neekle {
            onAnyConflict(BindAction.add)
            bind { "instance1" }
            bind { "instance2" }
        }.injector

        { injector<String>() } shouldThrow CannotCreateParticle::class that
                match { it.cause is SeveralParticlesFound }
        injector.getAll<String>() shouldEqual listOf("instance1", "instance2")
    }

    @Test fun `registering several string can be get as collection with add policy on type`() {
        val injector = Neekle {
            onConflictOf<String>(BindAction.add)
            bind { "instance1" }
            bind { "instance2" }
        }.injector

        { injector<String>() } shouldThrow CannotCreateParticle::class that
                match { it.cause is SeveralParticlesFound }

        injector.getAll<String>() shouldEqual listOf("instance1", "instance2")
    }

    @Test fun `registering several charsequence fails with add policy on String`() {
        {
            Neekle {
                onConflictOf<String>(BindAction.add)
                bind<CharSequence> { "instance1" }
                bind<CharSequence> { "instance2" }
            }
        } shouldThrow BindingInConflict::class
    }

    @Test fun `registering several string fails with add policy on charsequence`() {
        val injector = Neekle {
            onConflictOf<CharSequence>(BindAction.add)
            bind { "instance1" }
            bind { "instance2" }
        }.injector

        { injector<CharSequence>() } shouldThrow CannotCreateParticle::class that
                match { it.cause is SeveralParticlesFound }
        injector.getAll<CharSequence>() shouldEqual listOf("instance1", "instance2")
    }

    @Test fun `cannot register several subclasses when declared conflict fail`() {
        {
            Neekle {
                onConflictOf<Any>(BindAction.fail)
                bind { "value" }
                bind { 3 }
            }
        } shouldThrow BindingAlreadyPresent::class
    }

    @Test fun `ignore only keep the first`() {
        val neekle = Neekle {
            onConflictOf<String>(BindAction.ignore)
            bind { "retained" }
            bind { "ignored" }
            bind { "ignored bis" }
        }

        neekle<String>() shouldEqual "retained"
    }

    @Test fun `replace only keep the last`() {
        val neekle = Neekle {
            onConflictOf<String>(BindAction.replace)
            bind { "ignored" }
            bind { "ignored bis" }
            bind { "retained" }
        }

        neekle<String>() shouldEqual "retained"
    }
}