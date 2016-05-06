package com.kevinmost.kotlin_toolbelt.extension

import org.junit.Test

class ListUtilTest {
  @Test fun `test setOrAppend() with append`() {
    val res = mutableListOf(3, 4, 5).setOrAppend(3, 4)
    assert(res.size == 4)
    assert(res[3] == 4)
  }

  @Test fun `test setOrAppend() with set`() {
    val res = mutableListOf(3, 4, 5).setOrAppend(0, 2)
    assert(res[0] == 2)
  }

  @Test fun `test setOrAppend() with really high index throws exception`() {
    try {
      val res = mutableListOf(3, 4, 5).setOrAppend(400, 6)
      assert(false)
    } catch(e: IndexOutOfBoundsException) {
    }
  }


}
