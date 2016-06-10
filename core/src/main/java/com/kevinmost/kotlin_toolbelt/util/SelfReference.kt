package com.kevinmost.kotlin_toolbelt.util

class SelfReference<T>(val initializer: SelfReference<T>.() -> T) {
  val self: T by lazy {
    inner ?: throw IllegalStateException("Do not use `self` until initialized.")
  }

  private val inner = initializer()
}

/**
 * Allows referencing "this" (via the "self" variable) while initializing this value
 *
 * See: http://stackoverflow.com/q/35100389 for the idea and a sample.
 */
fun <T> selfReference(initializer: SelfReference<T>.() -> T): T {
  return SelfReference(initializer).self
}

