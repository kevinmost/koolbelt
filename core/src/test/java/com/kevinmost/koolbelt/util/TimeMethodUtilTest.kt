package com.kevinmost.koolbelt.util

import org.junit.Assert
import org.junit.Test

class TimeMethodUtilTest {
  @Test fun `test time method`() {
    val (result, ns) = measureNanoTime {
      Thread.sleep(1000)
      5
    }
    Assert.assertTrue(ns > 1000 * 1000)
    println("Took $ns nanoseconds")
    Assert.assertEquals(5, result)
  }
}

