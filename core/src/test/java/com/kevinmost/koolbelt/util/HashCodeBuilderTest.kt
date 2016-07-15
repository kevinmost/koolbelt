package com.kevinmost.koolbelt.util

import org.junit.Assert
import org.junit.Test

class HashCodeBuilderTest {
  @Test fun `test hashCode`() {
    val d1 = Data()
    val d2 = Data()
    Assert.assertTrue(d1.hashCode() == d2.hashCode())
  }
}

class Data {
  val bool: Boolean = true
  val byte: Byte = 8
  val char: Char = 'a'
  val short: Short = 4
  val int: Int = 3
  val long: Long = 12
  val float: Float = 3.4232532F
  val double: Double = 56809684645694.5234223
  val booleanArray: BooleanArray = booleanArrayOf(true, false, true, true, true)
  val byteArray: ByteArray = byteArrayOf(2, 5, -1, 127)
  val charArray: CharArray = charArrayOf('a', '3', 'q', '|')
  val shortArray: ShortArray = shortArrayOf(42, 5345, 5552, 22222)
  val intArray: IntArray = intArrayOf(234, 5843905, 73289473, 34242)
  val longArray: LongArray = longArrayOf(82903482, 84902348, 348795, 483290790739856)
  val floatArray: FloatArray = floatArrayOf(34.234F, 2348.2438242F)
  val doubleArray: DoubleArray = doubleArrayOf(28349024234.9234234, 243824982.423526)
  val objectArray: Array<*> = arrayOf("fjewiofw", "jfiwoj9023j9023f2903")
  val obj: Any = treeOf(4785748953) { node(3724724927497294) }

  private val _hashCodeAndEquals = hashCodeAndEquals(
      Data::bool,
      Data::byte,
      Data::char,
      Data::short,
      Data::int,
      Data::long,
      Data::float,
      Data::double,
      Data::booleanArray,
      Data::byteArray,
      Data::charArray,
      Data::shortArray,
      Data::intArray,
      Data::longArray,
      Data::floatArray,
      Data::doubleArray,
      Data::objectArray,
      Data::obj
  )

  override fun hashCode() = _hashCodeAndEquals.getHashCode()

  override fun equals(other: Any?) = _hashCodeAndEquals.getEquals(other)
}

