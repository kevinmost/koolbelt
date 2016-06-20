package com.kevinmost.koolbelt.extension

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class ListUtilTest {
  @Test fun `test setOrAppend() with append`() {
    val res = mutableListOf(3, 4, 5).setOrAppend(3, 4)
    assertEquals(4, res.size)
    assertEquals(4, res[3])
  }

  @Test fun `test setOrAppend() with set`() {
    val res = mutableListOf(3, 4, 5).setOrAppend(0, 2)
    assertEquals(2, res[0])
  }

  @Test fun `test setOrAppend() with really high index throws exception`() {
    try {
      mutableListOf(3, 4, 5).setOrAppend(400, 6)
      fail()
    } catch(e: IndexOutOfBoundsException) {
    }
  }


}
