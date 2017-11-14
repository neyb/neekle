package neekle

enum class BindAction {
    ignore, add, replace, fail;

    internal val always: PolicyElement by lazy { PolicyElement { this } }
}