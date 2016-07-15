package com.kevinmost.koolbelt.util

import java.util.*

fun <T> T.hashCodeAndEquals(vararg memberAccessors: (T) -> Any?): HashCodeAndEquals<T> {
  return HashCodeAndEquals(this, memberAccessors)
}

class HashCodeAndEquals<T>
internal constructor(
    receiver: T,
    private val memberAccessors: Array<out (T) -> Any?>
) {

  private val receiver by weakReference(receiver)

  fun getHashCode(): Int {
    val receiver = receiver ?: return 0
    return memberAccessors.fold(17) { accumulator, memberAccessor ->
      val thisMember = memberAccessor(receiver)
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

  fun getEquals(other: Any?): Boolean {
    val receiver = receiver ?: return other == null

    if (other == null) return false

    if (receiver === other) return true

    @Suppress("UNCHECKED_CAST")
    (other as? T ?: return false)

    memberAccessors.forEach { memberAccessor ->
      if (memberAccessor(receiver) != memberAccessor(other)) {
        return false
      }
    }

    return true
  }
}
