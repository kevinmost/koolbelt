package com.kevinmost.kotlin_extension


inline fun <T, R> mapEach(vararg elements: T, mapFunction: (T) -> R): List<R> {
    return elements.map(mapFunction)
}

fun <T : Any?> T.javaClass(): Class<T>? {
    // The Kotlin .javaClass extension doesn't handle unbound generics well, since they can be null
    if (this == null) {
        return null
    }
    @Suppress("CAST_NEVER_SUCCEEDS")
    return (this as Any).javaClass as Class<T>
}