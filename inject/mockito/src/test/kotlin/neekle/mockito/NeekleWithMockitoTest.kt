//package neekle.mockito
//
//import io.github.neyb.shoulk.shouldEqual
//import neekle.inject.api.Injector
//import org.junit.jupiter.api.Test
//import org.mockito.ArgumentMatchers
//import org.mockito.Mockito.*
//
//@Suppress("MemberVisibilityCanPrivate")
//class NeekleWithMockitoTest {
//
//    class ToMock {
//        fun stuffToMock(value: String) {
//            println(value)
//            System.err.println("azeaze")
//        }
//    }
//
//    class Subject(injector: Injector) {
//        private val toMock = injector<ToMock>()
//        private val value = injector<String>()
//
//        fun call() {
//            toMock.stuffToMock(value)
//        }
//    }
//
//    val inj = mockitoInjector {
//        with("value")
//    }
//
//    val mock: ToMock = inj()
//    val subject = Subject(inj)
//
//    @Test fun `just a simple mock test`() {
//        var called = false
//
//        `when`(mock.stuffToMock("value")).thenAnswer {
//            called = true
//            Unit
//        }
//
//        subject.call()
//
//        called shouldEqual true
//    }
//}