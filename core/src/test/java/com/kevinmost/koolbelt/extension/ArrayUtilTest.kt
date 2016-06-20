package com.kevinmost.koolbelt.extension

import org.junit.Assert.assertEquals
import org.junit.Test

class ArrayUtilTest {
  @Test fun `test mapToArray()`() {
    arrayOf("0", "1", "2")
        .mapToArray { it.toInt() }
        .forEachIndexed { i, value -> assertEquals(i, value) }
  }
}
