package neekle

import io.github.neyb.shoulk.shouldEqual
import org.junit.jupiter.api.Test

class SubclassNeekleTest {
    @Test fun `a String can be found as Object`() {
        val injector = Neekle {
            bind<String>() with Singleton { "yolo" }
        }.injector

        injector<Object>() shouldEqual "yolo"
    }

    @Test internal fun `a String and int can be get as Object`() {
        val injector = Neekle {
            bind<String>() with Singleton { "yolo" }
            bind<Number>() with Singleton { 3 }
        }.injector

        injector.getAll<Any>() shouldEqual listOf("yolo", 3)
    }
}