package com.kevinmost.kotlin_toolbelt.extension

import org.junit.Test

class ArrayUtilTest {
  @Test fun `test mapToArray()`() {
    arrayOf("0", "1", "2")
        .mapToArray { it.toInt() }
        .forEachIndexed { i, value -> assert(i == value) }
  }
}
