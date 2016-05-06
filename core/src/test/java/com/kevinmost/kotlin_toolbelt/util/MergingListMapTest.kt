package com.kevinmost.kotlin_toolbelt.util

import com.kevinmost.kotlin_toolbelt.util.mergingListsMapOf
import org.junit.Test

class MergingListMapTest {
  @Test fun `test plus-equals pair`() {
    var map = mergingListsMapOf("Foo" to listOf(1))
    assert(map.size == 1)
    assert(map["Foo"]?.size == 1)

    map += "Bar" to listOf(2)
    assert(map.size == 2)
    assert(map["Foo"]?.size == 1)
    assert(map["Bar"]?.size == 1)

    map += "Foo" to listOf(3, 4, 5)
    assert(map.size == 2)
    assert(map["Foo"]?.contains(3) ?: false)
    assert(map["Foo"]?.size == 4)

    map += "Foo" to listOf(7)
    assert(map.size == 2)
    assert(map["Foo"]?.contains(7) ?: false)
    assert(map["Foo"]?.size == 5)
  }
}