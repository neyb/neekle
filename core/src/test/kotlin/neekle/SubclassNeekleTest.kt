package neekle

import io.github.neyb.shoulk.shouldEqual
import org.junit.jupiter.api.Test

class SubclassNeekleTest {
    @Test fun `a String can be found as Object`() {
        val neekle = Neekle {
            bind { "yolo" }
        }

        neekle<Any>() shouldEqual "yolo"
    }

    @Test fun `a String and int can be get as Object`() {
        val injector = Neekle {
            bind { "yolo" }
            bind<Number> { 3 }
        }.injector

        injector.getAll<Any>() shouldEqual listOf("yolo", 3)
    }
}