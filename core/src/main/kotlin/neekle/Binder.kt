package neekle

class Binder<in T>(
        private val bind: (ParticleProvider<T>) -> Unit) {

    infix fun with(provider: ParticleProvider<T>) = bind(provider)
}