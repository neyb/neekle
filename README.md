Neekle is a dependency injection library, we do injection, and injection only, but we do it well.

Status: in development

# Design & motivation

## Dependency injection only

We consider that an dependency injection framework should only manage injection.
Almost every single existing injection framework embed AOP features, we don't want that for us.

Our concern is to do injection (and injection only) but to do it well.

You can still create your owns unit as you want (wrapped is proxy,
subclassed dynamically or with a generated class...) : it's all up to you.

## Simplicity

No code generation, just a simple and powerfull dsl.
We consider that neekle is a libray, not a framework.

## Let you free & testability

The `neekle-inject-api` is a simple module (1 class and 1 interface) that let you use any other DI if you want
by reimplement the `Locator` interface.

we provide an [example](inject/spring/src/test/kotlin/neekle/inject/spring/SpringLocatorTest.kt) that show using spring context instead of `neekle-core` without changing
any class.

this low coupling also allow us to provide you a simple way to test your code without dependencies to `neekle-core`.

## Speed

no classpath scanning, no reflection, no file parsing : just compiled code

# How to use

## Install

for now neekle is not published anywhere... So the simplest way is probably something that looks like that:
```
    git clone https://github.com/neyb/neekle.git
    cd neekle
    gradle wrapper
    ./gradlew
    cp core/build/libs/neekle-core.jar \
        inject/api/build/libs/neekle-inject-api.jar \
        ..
```

## Creating your first neekle

```kotlin
val neekle = Neekle {}
```

## Adding components and getting one

considering A: `class A(injector:Injector) { val b = Injector<B>() }`,  
B: `class B(injector:Injector) { val c = Injector<C>() }`,  
and C : `class C`

Then creating your neekle looks like this:

```kotlin
val neekle = Neekle {
    bind { A(it) }
    bind { B(it) }
    bind { C() }
}
```

you can then get you main component (A) with `neekle<A>()`

## binding type

## Conflict management

## SubModules

## DefaultModules

# TODOs

## to impl

- test mock
- creation interceptor / provider decorator
- publish
- yaml conf

## to document

- prototype
- force policy
- `easy import` strategy
- readme & doc
