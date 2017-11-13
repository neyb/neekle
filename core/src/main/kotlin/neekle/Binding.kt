package neekle

class Binding<out T>(
        val definition: BindingDefinition,
        val provider: ParticleProvider<T>)