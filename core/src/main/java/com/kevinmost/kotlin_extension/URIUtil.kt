package com.kevinmost.kotlin_extension

import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URI

fun URI.readToByteArray(): ByteArray {
  return toURL().openStream().readToByteArray()
}

private fun InputStream.readToByteArray(): ByteArray {
  use {
    ByteArrayOutputStream().use {
      val byteChunk = ByteArray(4096)

      var n: Int
      while (true) {
        n = read(byteChunk)
        if (n <= 0) {
          break;
        }
        it.write(byteChunk, 0, n)
      }
      return it.toByteArray()
    }
  }
}

