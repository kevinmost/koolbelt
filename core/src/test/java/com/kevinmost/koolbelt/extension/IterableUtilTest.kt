package com.kevinmost.koolbelt.extension

import org.junit.Assert
import org.junit.Test

class IterableUtilTest {
  @Test fun `test every function with empty list`() {
    emptyList<String>().every(5) { Assert.fail("This closure should never be executed!") }
  }

  @Test fun `test splitEvery function with empty list`() {
    emptyList<String>().splitEvery(5)
        .forEach { Assert.fail("This closure should never be executed!") }
  }

  @Test fun `test every with divisible list`() {
    (1..200).every(5) { list ->
      Assert.assertEquals(5, list.size)
    }
  }

  @Test fun `test splitEvery function with divisible list`() {
    val lists = (1..200).splitEvery(5)
    Assert.assertEquals(200 / 5, lists.size)
    lists.forEach { list ->
      Assert.assertEquals(5, list.size)
    }
    Assert.assertEquals(1, lists.first().first())
    Assert.assertEquals(200, lists.last().last())
  }

  @Test fun `test splitEvery function with list that is not long enough`() {
    val lists = listOf(0, 1, 2).splitEvery(5)
    Assert.assertEquals(1, lists.size)
  }

  @Test fun `test splitEvery with divisible list is reversible`() {
    val originalList: List<Int> = (1..200).map { it }
    val splitAndThenBack = originalList.splitEvery(5).flatten()

    Assert.assertArrayEquals(
        originalList.toTypedArray(),
        splitAndThenBack.toTypedArray()
    )
  }

  @Test fun `test splitEvery with only leftovers`() {
    val list = listOf(0, 1, 2).splitEvery(5)
    Assert.assertEquals(1, list.size)
    list.forEach {
      Assert.assertEquals(3, it.size)
    }
  }

  @Test fun `test splitEvery we can choose not to keep leftovers`() {
    val list = listOf(0, 1, 2).splitEvery(5, useLeftoversAtEnd = false)
    Assert.assertEquals(0, list.size)
  }
}