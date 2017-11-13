package neekle

data class BindingCriteria<out T>(val targetType: Class<out T>, val name: String?)