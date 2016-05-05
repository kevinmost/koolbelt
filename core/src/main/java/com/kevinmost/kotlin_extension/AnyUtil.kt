package com.kevinmost.kotlin_extension

fun <T : Any?> T.javaClass(): Class<T>? {
  // The Kotlin .javaClass extension doesn't handle unbound generics well, since they can be null
  if (this == null) {
    return null
  }
  @Suppress("CAST_NEVER_SUCCEEDS")
  return (this as Any).javaClass as Class<T>
}