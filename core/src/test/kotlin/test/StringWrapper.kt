package test

import neekle.Injector

class StringWrapper(injector: Injector){
    val value:String = injector()
}