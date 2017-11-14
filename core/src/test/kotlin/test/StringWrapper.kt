package test

import neekle.inject.api.Injector

class StringWrapper(injector: Injector){
    val value:String = injector()
}