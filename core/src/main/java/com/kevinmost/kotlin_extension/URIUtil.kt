package com.kevinmost.kotlin_extension

import java.io.InputStream
import java.net.URI

fun URI.readToByteArray(estimatedSizeInBytes: Int = DEFAULT_BUFFER_SIZE): ByteArray {
  return toURL().openStream().readToByteArray(estimatedSizeInBytes)
}

private fun InputStream.readToByteArray(estimatedSizeInBytes: Int) : ByteArray {
  return use { readBytes(estimatedSizeInBytes) }
}

