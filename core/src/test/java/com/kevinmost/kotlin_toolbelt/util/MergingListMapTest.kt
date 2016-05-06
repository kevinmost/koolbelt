package com.kevinmost.kotlin_toolbelt.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MergingListMapTest {
  @Test fun `test plus-equals pair`() {
    var map = mergingListsMapOf("Foo" to listOf(1))
    assertEquals(map.size, 1)
    assertEquals(map["Foo"]?.size, 1)

    map += "Bar" to listOf(2)
    assertEquals(map.size, 2)
    assertEquals(map["Foo"]?.size, 1)
    assertEquals(map["Bar"]?.size, 1)

    map += "Foo" to listOf(3, 4, 5)
    assertEquals(map.size, 2)
    assertTrue(map["Foo"]!!.contains(3))
    assertEquals(map["Foo"]?.size, 4)

    map += "Foo" to listOf(7)
    assertEquals(map.size, 2)
    assertTrue(map["Foo"]!!.contains(7))
    assertEquals(map["Foo"]?.size, 5)
  }
}