package com.kevinmost.kotlin_toolbelt.extension.android

import android.net.Uri
import com.kevinmost.kotlin_toolbelt.extension.readToByteArray
import java.net.URI

fun URI.convert(): Uri {
  return Uri.parse(toString())
}

fun Uri.convert(): URI {
  return URI(toString())
}

fun Uri.readToByteArray(estimatedSizeInBytes: Int = DEFAULT_BUFFER_SIZE): ByteArray {
  return convert().readToByteArray(estimatedSizeInBytes)
}
