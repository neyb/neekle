package neekle

import io.github.neyb.shoulk.matcher.match
import io.github.neyb.shoulk.shouldEqual
import io.github.neyb.shoulk.shouldThrow
import io.github.neyb.shoulk.that
import neekle.BindAction.*
import org.junit.jupiter.api.Test

class NeekleConflictTest {

//    @Test fun `registering several binding in conflict should fail`() {
//        {
//            Neekle {
//                bind { "instance1" }
//                bind { "instance2" }
//            }
//        } shouldThrow BindingInConflict::class
//    }

//    @Test fun `registering several binding in conflict should fail with fail policy`() {
//        {
//            Neekle {
//                onAnyConflict(fail)
//                bind { "instance1" }
//                bind { "instance2" }
//            }
//        } shouldThrow BindingAlreadyPresent::class
//    }

//    @Test fun `registering several string can be get as collection with add policy`() {
//        val injector = Neekle {
//            onAnyConflict(add)
//            bind { "instance1" }
//            bind { "instance2" }
//        }.injector
//
//        { injector<String>() } shouldThrow SeveralBindingsFound::class that
//                match { it.message == "several potential bindings for =>java.lang.String: [=>java.lang.String, =>java.lang.String]" }
//        injector.getAll<String>() shouldEqual listOf("instance1", "instance2")
//    }

//    @Test fun `registering several string can be get as collection with add policy on type`() {
//        val injector = Neekle {
//            onConflict<String>(add)
//            bind { "instance1" }
//            bind { "instance2" }
//        }.injector
//
//        { injector<String>() } shouldThrow SeveralBindingsFound::class
//        injector.getAll<String>() shouldEqual listOf("instance1", "instance2")
//    }

//    @Test fun `registering several charsequence fails with add policy on String`() {
//        {
//            Neekle {
//                onConflict<String>(add)
//                bind<CharSequence> { "instance1" }
//                bind<CharSequence> { "instance2" }
//            }
//        } shouldThrow BindingInConflict::class
//    }

//    @Test fun `registering several string fails with add policy on charsequence`() {
//        val injector = Neekle {
//            onConflict<CharSequence>(add)
//            bind { "instance1" }
//            bind { "instance2" }
//        }.injector
//
//        { injector<CharSequence>() } shouldThrow SeveralBindingsFound::class that
//                match { it.message == "several potential bindings for =>java.lang.CharSequence: [=>java.lang.String, =>java.lang.String]" }
//        injector.getAll<CharSequence>() shouldEqual listOf("instance1", "instance2")
//    }

//    @Test fun `cannot register several subclasses when declared conflict fail`() {
//        {
//            Neekle {
//                onConflict<Any>(fail)
//                bind { "value" }
//                bind { 3 }
//            }
//        } shouldThrow BindingAlreadyPresent::class
//    }

//    @Test fun `ignore only keep the first`() {
//        val neekle = Neekle {
//            onConflict<String>(ignore)
//            bind { "retained" }
//            bind { "ignored" }
//            bind { "ignored bis" }
//        }
//
//        neekle<String>() shouldEqual "retained"
//    }

//    @Test fun `replace only keep the last`() {
//        val neekle = Neekle {
//            onConflict<String>(replace)
//            bind { "ignored" }
//            bind { "ignored bis" }
//            bind { "retained" }
//        }
//
//        neekle<String>() shouldEqual "retained"
//    }

    @Test fun `no conflict if names are different`() {
        val neekle = Neekle {
            bind("name1") { "value1" }
            bind("name2") { "value2" }
        }

        neekle<String>("name1") shouldEqual "value1"
        neekle<String>("name2") shouldEqual "value2"
    }

//    @Test fun `conflict if first item has no name`() {
//        {
//            Neekle {
//                bind { "value1" }
//                bind("name") { "value2" }
//            }
//        } shouldThrow BindingInConflict::class
//    }

//    @Test fun `conflict if second item has no name`() {
//        {
//            Neekle {
//                bind("name") { "value1" }
//                bind { "value2" }
//            }
//        } shouldThrow BindingInConflict::class
//    }
}