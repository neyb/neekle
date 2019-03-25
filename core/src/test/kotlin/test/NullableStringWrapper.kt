package test

import neekle.Injector

class NullableStringWrapper(injector: Injector){
    val value:String? = injector()
}