package com.kevinmost.koolbelt.extension

import org.junit.Assert
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

  @Test fun `test operator overload for sublist`() {
    val numbers = (1..100).toList()

    val sub = numbers[50..52]
    Assert.assertEquals(2, sub.size)
    Assert.assertEquals(51, sub[0])
    Assert.assertEquals(52, sub[1])

    val sub2 = numbers[0..0]
    Assert.assertTrue(sub2.isEmpty())

    val sub3 = numbers[10 downTo 0 step 5]
    Assert.assertEquals(3, sub3.size)
    Assert.assertEquals(11, sub3[0])
    Assert.assertEquals(6, sub3[1])
    Assert.assertEquals(1, sub3[2])
  }
}
