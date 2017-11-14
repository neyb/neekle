package neekle

import org.junit.jupiter.api.Test

class InstanceNeekleTest {
    @Test fun `instance registers the instance`() {
        Neekle {
            bind<String>() to "value"
        }
    }
}