package neekle

import neekle.inject.api.Locator

class ComposedLocator(
        private val locators: Set<Locator>,
        private val defaultLocators: Set<Locator>
                     ) : Locator {


    override fun <T> getAll(type: Class<T>, name: String?) =
            locators.flatMap { it.getAll(type, name) }.takeIf { it.isNotEmpty() }
                    ?: defaultLocators.flatMap { it.getAll(type, name) }

}
