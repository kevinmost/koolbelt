package com.kevinmost.koolbelt.extension

import org.junit.Assert
import org.junit.Test

class CollectionUtilTest {
  @Test fun `test that collections cannot be cast to mutable`() {
    val list = listOf("1", "2", "3")
    try {
      list.toImmutable() as MutableList<String>
      Assert.fail("These shouldn't be castable to mutable!")
    } catch (e: ClassCastException) {
      Assert.assertArrayEquals(arrayOf("1", "2", "3"), list.toTypedArray())
    }
  }
}
