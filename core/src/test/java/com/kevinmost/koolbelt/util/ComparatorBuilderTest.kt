package com.kevinmost.koolbelt.util

import org.junit.Assert
import org.junit.Test

class ComparatorBuilderTest {
  @Test fun `test comparator builder for both ascending and descending`() {
    // this comparator sorts the strings first by length, shortest to longest. For equal length strings, it sorts them reverse-lexographically
    val comparator = buildComparator<String> {
      withComparison { it.length }
      withComparison(descending = true) { it }
    }
    val sorted = listOf("foo", "fooo", "fooooo", "fop").sortedWith(comparator).toTypedArray()

    Assert.assertArrayEquals(
        arrayOf("fop", "foo", "fooo", "fooooo"),
        sorted
    )
  }
}
