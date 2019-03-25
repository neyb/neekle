package samples

import neekle.Configuration

@Suppress("MemberVisibilityCanPrivate")
class ResolvingConfictBetweenConfigurationTest {

    val defaultConfiguration1: Configuration = { bind { "value1" } }
    val defaultConfiguration2: Configuration = { bind { "value2" } }

//    val defaultConfigurationWithDefaultConflictAction1: Configuration = {
//        onAnyConflict(BindAction.fail)
//        bind { "value1" }
//    }
//    val defaultConfigurationWithDefaultConflictAction2: Configuration = {
//        onAnyConflict(BindAction.fail)
//        bind { "value2" }
//    }

//    @Test fun `a conflict can exist within two default configuration`() {
//        {
//            Neekle {
//                defaultModule(defaultConfigurationWithDefaultConflictAction1)
//                defaultModule(defaultConfiguration2)
//            }
//        } shouldThrow BindingInConflict::class
//    }

//    @Test fun `resolving a conflict between two default configuration`() {
//        Neekle {
//            defaultModule(defaultConfigurationWithDefaultConflictAction1)
//            defaultModule(defaultConfiguration2)
//
//            onConflict<String>(BindAction.ignore)
//        }<String>() shouldEqual "value1"
//    }

//    @Test fun `a conflict can exist within two default configuration with default action`() {
//        {
//            Neekle {
//                defaultModule(defaultConfigurationWithDefaultConflictAction1)
//                defaultModule(defaultConfigurationWithDefaultConflictAction2)
//            }
//        } shouldThrow BindingAlreadyPresent::class
//    }

//    @Test fun `resolving a conflict between two default configuration with default action`() {
//        Neekle {
//            defaultModule(defaultConfigurationWithDefaultConflictAction1)
//            defaultModule(defaultConfigurationWithDefaultConflictAction2)
//
//            onConflict<String>(BindAction.ignore)
//        }<String>() shouldEqual "value1"
//    }

//    @Test
//    fun `a conflict can exist within two default configuration with default action (bis)`() {
//        val function: () -> Unit = {
//            Neekle {
//                defaultModule {
//                    submodule { bind { "value1" } }
//                }
//
//                defaultModule {
//                    onAnyConflict(BindAction.fail)
//                    submodule { bind { "value2" } }
//                }
//            }
//        }
//
//        function shouldThrow BindingAlreadyPresent::class
//    }

//    @Test fun `resolving a conflict between two default configuration with default action (bis)`() {
//        Neekle {
//            defaultModule {
//                onAnyConflict(BindAction.fail)
//                submodule { bind { "value1" } }
//            }
//
//            defaultModule {
//                onAnyConflict(BindAction.fail)
//                submodule { bind { "value2" } }
//            }
//
//            onConflict<String>(BindAction.ignore)
//        }<String>() shouldEqual "value1"
//    }
}
