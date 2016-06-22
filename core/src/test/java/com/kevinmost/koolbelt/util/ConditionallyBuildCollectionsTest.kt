package com.kevinmost.koolbelt.util

import org.junit.Assert
import org.junit.Test

@Suppress("SimplifyBooleanWithConstants")
class ConditionallyBuildCollectionsTest {
  @Test fun `test conditionally built map`() {
    val map = buildMap<String, Int> {
      addIf(3 > 2) { "yes" to 2 }
      addIf(3 > 4, element = "no" to 4)
      addAllIf(3 > 0) { mapOf("yes2" to 2, "yes3" to 5) }
      addAllIf(0 > 3, mapOf("no2" to 5, "no3" to 8))
    }

    arrayOf("yes", "yes2", "yes3").forEach {
      Assert.assertTrue(map.containsKey(it))
    }
    arrayOf("no", "no2", "no3").forEach {
      Assert.assertFalse(map.containsKey(it))
    }
  }

  @Test fun `test conditionally built list`() {
    val list = buildList<String> {
      addIf(3 > 2) { "yes" }
      addIf(3 > 4, "no")
      addAllIf(3 > 0) { listOf("yes2", "yes3") }
      addAllIf(0 > 3, setOf("no2", "no3"))
    }

    arrayOf("yes", "yes2", "yes3").forEach {
      Assert.assertTrue(list.contains(it))
    }
    arrayOf("no", "no2", "no3").forEach {
      Assert.assertFalse(list.contains(it))
    }
  }
}