package com.kevinmost.kotlin_toolbelt.util

import org.junit.Assert
import org.junit.Test

class TreeTest {
  @Test fun `test immutable tree`() {
    val tree = treeOf(5) {
      node(2) {
        node(3)
      }
      node(3) {
        node(8) {
          node(22)
          node(23)
        }
      }
    }
    Assert.assertEquals(5, tree.value)
    Assert.assertEquals(2, tree[0].value)
    Assert.assertEquals(3, tree[0][0].value)
    Assert.assertEquals(3, tree[1].value)
    Assert.assertEquals(8, tree[1][0].value)
    Assert.assertEquals(22, tree[1][0][0].value)
    Assert.assertEquals(23, tree[1][0][1].value)
  }
}
