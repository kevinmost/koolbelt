package com.kevinmost.koolbelt.extension.android

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

// Copied from Anko's ContextUtils and adapted to take in a Bundle
fun bundleOf(vararg params: Pair<String, Any?>) = Bundle().put(*params)

fun Bundle.put(vararg params: Pair<String, Any?>): Bundle {
  for ((k, v) in params) {
    when (v) {
      null -> putString(k, null)
      is Boolean -> putBoolean(k, v)
      is Byte -> putByte(k, v)
      is Char -> putChar(k, v)
      is Short -> putShort(k, v)
      is Int -> putInt(k, v)
      is Long -> putLong(k, v)
      is Float -> putFloat(k, v)
      is Double -> putDouble(k, v)
      is String -> putString(k, v)
      is CharSequence -> putCharSequence(k, v)
      is Parcelable -> putParcelable(k, v)
      is Serializable -> putSerializable(k, v)
      is BooleanArray -> putBooleanArray(k, v)
      is ByteArray -> putByteArray(k, v)
      is CharArray -> putCharArray(k, v)
      is DoubleArray -> putDoubleArray(k, v)
      is FloatArray -> putFloatArray(k, v)
      is IntArray -> putIntArray(k, v)
      is LongArray -> putLongArray(k, v)
      is Array<*> -> {
        @Suppress("UNCHECKED_CAST")
        when {
          v.isArrayOf<Parcelable>() -> putParcelableArray(k, v as Array<out Parcelable>)
          v.isArrayOf<CharSequence>() -> putCharSequenceArray(k, v as Array<out CharSequence>)
          v.isArrayOf<String>() -> putStringArray(k, v as Array<out String>)
          else -> throw IllegalArgumentException("Unsupported bundle component (${v.javaClass})")
        }
      }
      is ShortArray -> putShortArray(k, v)
      is Bundle -> putBundle(k, v)
      else -> throw IllegalArgumentException("Unsupported bundle component (${v.javaClass})")
    }
  }
  return this
}
