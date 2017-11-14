package neekle

enum class BindAction {
    ignore, add, replace, fail;

    val always: PolicyElement by lazy { PolicyElement { this@BindAction } }
}