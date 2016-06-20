package com.kevinmost.koolbelt.util

inline fun <V> treeOf(root: V, childBuilder: Tree.Builder<V>.() -> Unit): Tree<V> {
  return Tree.Builder(root).apply { childBuilder() }.__build(parent = null)
}

data class Tree<V> internal constructor(
    val value: V,
    val children: List<Tree<V>>,
    val parent: Tree<V>?
) : List<Tree<V>> by children {

  class Builder<V>(val root: V) {

    val __children = mutableListOf<Builder<V>>()

    inline fun node(value: V, childBuilder: Builder<V>.() -> Unit) {
      __children.add(Builder(value).apply { childBuilder() })
    }

    // TODO: Remove this function and make childBuilder optional when we're allowed to have optional inlined-function params
    fun node(value: V) {
      node(value) {}
    }

    fun __build(parent: Tree<V>?): Tree<V> {
      val tree = Tree(value = root,
          parent = parent,
          children = mutableListOf<Tree<V>>()
      )
      // HACK! We had to build the Tree itself before adding any of its children because we need
      // the children to be able to reference their parent (which is this aforementioned Tree...)
      // Good thing Kotlin "immutable" Collections aren't really immutable... :X
      (tree.children as MutableList<Tree<V>>).addAll(__children.map { it.__build(tree) })
      return tree
    }
  }
}

