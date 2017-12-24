package neekle.mockito

import neekle.inject.api.Injector
import neekle.test.MockInjectorConfigurer
import neekle.test.MockLocator
import neekle.test.Mocker
import org.mockito.Mockito

fun mockitoInjector(configuration: MockInjectorConfigurer.() -> Unit = {}) =
        Injector(MockLocator(MockitoMocker, configuration))

object MockitoMocker : Mocker {
    override fun <T> mock(type: Class<T>): T {
        return Mockito.mock(type)
    }
}