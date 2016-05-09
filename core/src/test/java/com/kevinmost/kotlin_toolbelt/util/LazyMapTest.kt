package com.kevinmost.kotlin_toolbelt.util

import org.junit.Assert
import org.junit.Test

class LazyMapTest {

  val lazyMap = LazyMap<String, Int> { it.length }

  @Test fun `test lazy map`() {
    Assert.assertEquals(3, lazyMap["Foo"])
    Assert.assertEquals(1, lazyMap["F"])
  }
}
