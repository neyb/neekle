package test

fun Throwable.withCauses(): List<Throwable> = generateSequence({ this }, { it.cause }).toList()