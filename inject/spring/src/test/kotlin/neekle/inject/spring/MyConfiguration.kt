package neekle.inject.spring

import neekle.inject.api.Injector
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration open class MyConfiguration {
    @Bean open fun injector(applicationContext: ApplicationContext) = Injector(SpringLocator(applicationContext))
}

interface MyComponent

annotation class MyAnnotation

@Component class MyComponentA(inject: Injector) {
    val componentB: MyComponentB = inject()
    val myComponents: Collection<neekle.inject.spring.MyComponent> = inject.getAll()
    val annotatedComponent: Collection<neekle.inject.spring.MyComponent> = inject.getAll("@neekle.inject.spring.MyAnnotation")
}

@Component class MyComponentB : neekle.inject.spring.MyComponent

@MyAnnotation
@Component class MyComponentC : neekle.inject.spring.MyComponent