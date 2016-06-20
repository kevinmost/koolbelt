package com.kevinmost.koolbelt.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AddOnlyMapTest {
  @Test fun `test plus-equals pair`() {

    var map = addOnlyMapOf("Foo" to 1)
    assertEquals(1, map.size)
    assertEquals(1, map["Foo"])
    assertEquals(null, map["Bar"])

    map += "Bar" to 2
    assertEquals(2, map.size)
    assertEquals(1, map["Foo"])
    assertEquals(2, map["Bar"])
  }

  @Test fun `test plus-equals overwrite`() {
    var map = addOnlyMapOf("Foo" to listOf(1), "Bar" to listOf(2))
    map += "Foo" to listOf(3)

    map["Foo"]!!.let {
      assertTrue(it.contains(3))
      assertTrue(!it.contains(1))
    }
  }

  @Test fun `test plus-equals map`() {
    var m1 = addOnlyMapOf("Foo" to 1, "Bar" to 2)
    val m2 = addOnlyMapOf("Baz" to 3)

    m1 += m2

    assertEquals(3, m1.size)
    assertEquals(1, m1["Foo"])
    assertEquals(2, m1["Bar"])
    assertEquals(3, m1["Baz"])
  }

  @Test fun `test set`() {
    val map = addOnlyMapOf("Foo" to 1)
    assertEquals(1, map["Foo"])

    map["Foo"] = 2
    assertEquals(2, map["Foo"])

    assertEquals(1, map.size)
  }

  @Test fun `test plus pair`() {
    val combined = addOnlyMapOf("Foo" to 1) + ("Bar" to 2) + ("Baz" to 3)

    assertEquals(3, combined.size)
    assertEquals(1, combined["Foo"])
    assertEquals(2, combined["Bar"])
    assertEquals(3, combined["Baz"])
  }

  @Test fun `test plus map`() {
    val combined = addOnlyMapOf("Foo" to 1) + addOnlyMapOf("Bar" to 2) + addOnlyMapOf("Baz" to 3)

    assertEquals(3, combined.size)
    assertEquals(1, combined["Foo"])
    assertEquals(2, combined["Bar"])
    assertEquals(3, combined["Baz"])
  }
}
