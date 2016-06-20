package com.kevinmost.koolbelt.util

class SelfReference<T> {
  val self: T by lazy {
    __self ?: throw IllegalStateException("Do not use `self` until initialized.")
  }

  var __self: T? = null
}

/**
 * Allows referencing "this" (via the "self" variable) while initializing this value
 *
 * See: http://stackoverflow.com/q/35100389 for the idea and a sample. Rewritten slightly to
 * allow the [initializer] block to be inlined.
 */
inline fun <T> selfReference(initializer: SelfReference<T>.() -> T): T {
  return SelfReference<T>().apply { __self = initializer() }.self
}

