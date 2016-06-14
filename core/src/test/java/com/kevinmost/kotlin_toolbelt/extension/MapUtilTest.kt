package com.kevinmost.kotlin_toolbelt.extension

import org.junit.Assert.assertEquals
import org.junit.Test

class MapUtilTest {
  @Test fun `test filter non null keys`() {
    assertEquals(2, mapOf(3 to 4, 4 to 5, null to 5).filterNotNullKeys().size)
  }

  @Test fun `test filter non null values`() {
    assertEquals(2, mapOf(3 to 4, 4 to null, null to 5).filterNotNullValues().size)
  }

  @Test fun `test filter non null keys and values`() {
    assertEquals(1, mapOf(3 to 4, 4 to null, null to 5).filterNotNullKeysAndValues().size)
  }
}