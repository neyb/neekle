package neekle

class Binder<in T>(
        private val bindingDefinition: BindingDefinition,
        private val module: Module) {
    infix fun with(provider: ParticleProvider<T>) {
        module.addBinding(Binding(bindingDefinition, provider))
    }
}