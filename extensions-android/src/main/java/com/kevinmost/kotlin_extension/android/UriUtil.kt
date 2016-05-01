package com.kevinmost.kotlin_extension.android

import android.net.Uri
import com.kevinmost.kotlin_extension.readToByteArray
import java.net.URI

fun URI.convert(): Uri {
  return Uri.parse(toString())
}

fun Uri.convert(): URI {
  return URI(toString())
}

fun Uri.readToByteArray(): ByteArray {
  return convert().readToByteArray()
}
