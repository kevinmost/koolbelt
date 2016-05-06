package com.kevinmost.kotlin_extension

import com.kevinmost.util.addOnlyMapOf
import org.junit.Test

class AddOnlyMapTest {
  @Test fun `test plus-equals pair`() {

    var map = addOnlyMapOf("Foo" to 1)
    assert(map.size == 1)
    assert(map["Foo"] == 1)
    assert(map["Bar"] == null)

    map += "Bar" to 2
    assert(map.size == 2)
    assert(map["Foo"] == 1)
    assert(map["Bar"] == 2)
  }

  @Test fun `test plus-equals overwrite`() {
    var map = addOnlyMapOf("Foo" to listOf(1), "Bar" to listOf(2))
    map += "Foo" to listOf(3)

    map["Foo"]!!.let {
      assert(it.contains(3))
      assert(!it.contains(1))
    }
  }

  @Test fun `test plus-equals map`() {
    var m1 = addOnlyMapOf("Foo" to 1, "Bar" to 2)
    val m2 = addOnlyMapOf("Baz" to 3)

    m1 += m2

    assert(m1.size == 3)
    assert(m1["Foo"] == 1)
    assert(m1["Bar"] == 2)
    assert(m1["Baz"] == 3)
  }

  @Test fun `test set`() {
    val map = addOnlyMapOf("Foo" to 1)
    assert(map["Foo"] == 1)

    map["Foo"] = 2
    assert(map["Foo"] == 2)

    assert(map.size == 1)
  }

  @Test fun `test plus pair`() {
    val combined = addOnlyMapOf("Foo" to 1) + ("Bar" to 2) + ("Baz" to 3)

    assert(combined.size == 3)
    assert(combined["Foo"] == 1)
    assert(combined["Bar"] == 2)
    assert(combined["Baz"] == 3)
  }

  @Test fun `test plus map`() {
    val combined = addOnlyMapOf("Foo" to 1) + addOnlyMapOf("Bar" to 2) + addOnlyMapOf("Baz" to 3)

    assert(combined.size == 3)
    assert(combined["Foo"] == 1)
    assert(combined["Bar"] == 2)
    assert(combined["Baz"] == 3)
  }
}
