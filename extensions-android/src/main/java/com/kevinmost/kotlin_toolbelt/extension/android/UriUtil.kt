package com.kevinmost.kotlin_toolbelt.extension.android

import android.net.Uri
import com.kevinmost.kotlin_toolbelt.extension.ReadURIResult
import com.kevinmost.kotlin_toolbelt.extension.readToByteArray
import java.io.IOException
import java.net.URI

fun URI.convert(): Uri {
  return Uri.parse(toString())
}

fun Uri.convert(): URI {
  return URI(toString())
}

fun Uri.readToByteArray(estimatedSizeInBytes: Int = DEFAULT_BUFFER_SIZE): ReadUriResult {
  val result = convert().readToByteArray(estimatedSizeInBytes)
  return when(result) {
    is ReadURIResult.Success -> ReadUriResult.Success(result.bytes, result.uri.convert())
    is ReadURIResult.Failure -> ReadUriResult.Failure(result.err, result.uri.convert())
  }
}

sealed class ReadUriResult(val uri: Uri) {
  class Success(val bytes: ByteArray, uri: Uri) : ReadUriResult(uri) {
    operator fun component1() = bytes
  }
  class Failure(val err: IOException, uri: Uri) : ReadUriResult(uri) {
    operator fun component1() = err
  }

  operator fun component2() = uri

  fun getIfSuccessful(): Success? = this as? Success
}
