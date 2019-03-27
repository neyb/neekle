@file:Suppress("UNUSED_PARAMETER")

package test

class A

class B(a: A)

class BWithSeveralAs(severalAs: Collection<A>)

class C(b: B)

class StringWrapper(val value: String)

class NullableStringWrapper(val value: String?)