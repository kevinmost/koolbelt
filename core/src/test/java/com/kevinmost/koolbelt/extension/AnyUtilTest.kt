package com.kevinmost.koolbelt.extension

import org.junit.Assert.assertEquals
import org.junit.Test

class AnyUtilTest {
  @Test fun `test javaClass() method`() {
    assertEquals("String", "".javaClass()?.simpleName)
    assertEquals("Integer", 1.javaClass()?.simpleName)
    assertEquals(null, null.javaClass()?.simpleName)
  }
}
