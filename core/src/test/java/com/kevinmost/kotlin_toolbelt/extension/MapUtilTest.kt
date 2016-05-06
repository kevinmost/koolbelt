package com.kevinmost.kotlin_toolbelt.extension

import org.junit.Assert.assertEquals
import org.junit.Test

class MapUtilTest {
  @Test fun `test filter non null keys`() {
    assertEquals(2, mapOf(3 to 4, 4 to 5, null to 5).filterNotNullKeys().size)
  }
}