package com.kevinmost.koolbelt.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MergingListMapTest {
  @Test fun `test plus-equals pair`() {
    var map = mergingListsMapOf("Foo" to listOf(1))
    assertEquals(1, map.size)
    assertEquals(1, map["Foo"]?.size)

    map += "Bar" to listOf(2)
    assertEquals(2, map.size)
    assertEquals(1, map["Foo"]?.size)
    assertEquals(1, map["Bar"]?.size)

    map += "Foo" to listOf(3, 4, 5)
    assertEquals(2, map.size)
    assertTrue(map["Foo"]!!.contains(3))
    assertEquals(4, map["Foo"]?.size)

    map += "Foo" to listOf(7)
    assertEquals(2, map.size)
    assertTrue(map["Foo"]!!.contains(7))
    assertEquals(5, map["Foo"]?.size)
  }

  @Test fun `test set value doesn't overwrite`() {
    val map = mergingListsMapOf("Foo" to listOf(1))
    assertEquals(1, map["Foo"]!!.size)

    map["Foo"] = listOf(2)

    assertEquals(2, map["Foo"]!!.size)
  }
}