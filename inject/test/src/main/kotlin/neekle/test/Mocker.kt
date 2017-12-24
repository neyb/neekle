package neekle.test

interface Mocker {
    fun <T> mock(type: Class<T>): T
}