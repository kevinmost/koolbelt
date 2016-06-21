package com.kevinmost.koolbelt.extension

import java.io.IOException
import java.io.InputStream
import java.net.URI

fun URI.readToByteArray(estimatedSizeInBytes: Int = DEFAULT_BUFFER_SIZE): ReadURIResult {
  try {
    return ReadURIResult.Success(toURL().openStream().readToByteArray(estimatedSizeInBytes), this)
  } catch(e: IOException) {
    return ReadURIResult.Failure(e, this)
  }
}

private fun InputStream.readToByteArray(estimatedSizeInBytes: Int): ByteArray {
  return use { readBytes(estimatedSizeInBytes) }
}

sealed class ReadURIResult(val uri: URI) {
  class Success(val bytes: ByteArray, uri: URI) : ReadURIResult(uri) {
    operator fun component1() = bytes
  }

  class Failure(val err: IOException, uri: URI) : ReadURIResult(uri) {
    operator fun component1() = err
  }

  operator fun component2() = uri

  fun getIfSuccessful(): Success? = this as? Success
}
