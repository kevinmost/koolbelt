package com.kevinmost.koolbelt.util

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

  @Test fun `test parent reference works properly`() {
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
    Assert.assertTrue(tree[0].parent === tree)
    Assert.assertTrue(tree[1][0][1].parent?.parent === tree[1])
  }

  @Test fun `test sorted tree`() {
    val tree = sortedTreeOf(5) {
      node(2) {
        node(7)
        node(2)
        node(3)
      }
    }
    Assert.assertEquals(5, tree.value)
    Assert.assertEquals(2, tree[0].value)
    Assert.assertEquals(2, tree[0][0].value)
    Assert.assertEquals(3, tree[0][1].value)
    Assert.assertEquals(7, tree[0][2].value)
  }
  @Test fun `test sort function`() {
    val tree = treeOf(5) {
      node(2) {
        node(7)
        node(2)
        node(3)
      }
    }.sort { left, right -> left.compareTo(right) }
    Assert.assertEquals(5, tree.value)
    Assert.assertEquals(2, tree[0].value)
    Assert.assertEquals(2, tree[0][0].value)
    Assert.assertEquals(3, tree[0][1].value)
    Assert.assertEquals(7, tree[0][2].value)
  }
}
