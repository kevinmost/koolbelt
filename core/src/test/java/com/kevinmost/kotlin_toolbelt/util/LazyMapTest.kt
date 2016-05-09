package com.kevinmost.kotlin_toolbelt.util

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LazyMapTest {

  var lazyMap: MutableMap<String, Int>? = null

  @Before fun `setup lazyMap`() {
    lazyMap = mutableLazyMapOf(mapOf("Foo" to 2)) { string -> string.length }
  }

  @Test fun `test only initializes missing values`() {
    assertEquals(2, lazyMap!!["Foo"])
    assertEquals(1, lazyMap!!["F"])
  }

  @Test fun `test can insert values later`() {
    lazyMap!!["Foo"] = 4
    assertEquals(4, lazyMap!!["Foo"])
  }
}
