package test

import neekle.inject.api.Injector

class NullableStringWrapper(injector: Injector){
    val value:String? = injector()
}