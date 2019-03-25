package neekle.inject.api

import java.lang.Exception

interface Locator {
    operator fun <T> get(type: Class<T>, name: String? = null): T = getAll(type, name).let {
        when (it.size) {
            0 -> throw NoItemFound(type, name)
            1 -> it.first()
            else -> throw SeveralItemsFound(type, name)
        }
    }

    fun <T> getAll(type: Class<T>, name: String?): Collection<T>
}


class NoItemFound(type: Class<*>, name: String?) : Exception("no item found for type $type and name $name")

class SeveralItemsFound(type: Class<*>, name: String?) : Exception("several items found for type $type and name $name")