package com.kevinmost.kotlin_toolbelt.extension

// This is a workaround until the Kotlin stdlib provides a .use extension for Closeable
inline fun <T : AutoCloseable, R> T.use(block: (T) -> R): R {
  var closed = false
  try {
    return block(this)
  } catch (e: Exception) {
    closed = true
    try {
      this.close()
    } catch (closeException: Exception) {
      // eat the closeException as we are already throwing the original cause
      // and we don't want to mask the real exception

      // TODO on Java 7 we should call
      // e.addSuppressed(closeException)
      // to work like try-with-resources
      // http://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html#suppressed-exceptions
    }
    throw e
  } finally {
    if (!closed) {
      close()
    }
  }
}
