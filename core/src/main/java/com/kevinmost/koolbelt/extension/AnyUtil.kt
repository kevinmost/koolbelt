package com.kevinmost.koolbelt.extension

var Any?.print: Any?
  get() = throw UnsupportedOperationException("You cannot read the value of print; it is meant as an alias to the print() function")
  set(value) = print(value)

var Any?.println: Any?
  get() = throw UnsupportedOperationException("You cannot read the value of println; it is meant as an alias to the println() function")
  set(value) = println(value)

fun <T : Any?> T.javaClass(): Class<T>? {
  // The Kotlin .javaClass extension doesn't handle unbound generics well, since they can be null
  if (this == null) {
    return null
  }
  @Suppress("CAST_NEVER_SUCCEEDS")
  return (this as Any).javaClass as Class<T>
}