package com.kevinmost.koolbelt.util

import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <T> T.hashCodeBuilder(vararg membersToHash: (T) -> Any?): ReadOnlyProperty<T, Int> {
  return object : ReadOnlyProperty<T, Int> {
    override fun getValue(thisRef: T, property: KProperty<*>): Int {
      return membersToHash.fold(17) { accumulator, hashableMemberAccessor ->
        val thisMember = hashableMemberAccessor(thisRef)
        val add: Int = when (thisMember) {
          is Boolean -> if (thisMember) 1 else 0
          is Byte -> thisMember.toInt()
          is Char -> thisMember.toInt()
          is Short -> thisMember.toInt()
          is Int -> thisMember
          is Long -> (thisMember xor (thisMember ushr 32)).toInt()
          is Float -> java.lang.Float.floatToIntBits(thisMember)
          is Double -> {
            val doubleMemberBits: Long = java.lang.Double.doubleToLongBits(thisMember)
            (doubleMemberBits xor (doubleMemberBits ushr 32)).toInt()
          }
          is BooleanArray -> Arrays.hashCode(thisMember)
          is ByteArray -> Arrays.hashCode(thisMember)
          is CharArray -> Arrays.hashCode(thisMember)
          is ShortArray -> Arrays.hashCode(thisMember)
          is IntArray -> Arrays.hashCode(thisMember)
          is LongArray -> Arrays.hashCode(thisMember)
          is FloatArray -> Arrays.hashCode(thisMember)
          is DoubleArray -> Arrays.hashCode(thisMember)
          is Array<*> -> Arrays.hashCode(thisMember)
          else -> thisMember?.hashCode() ?: 0
        }
        return@fold (31 * accumulator) + add
      }
    }
  }
}

