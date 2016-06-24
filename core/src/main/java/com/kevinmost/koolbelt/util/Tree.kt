package com.kevinmost.koolbelt.util

import java.util.*

inline fun <V> treeOf(root: V, childBuilder: Builder<V>.() -> Unit): Tree<V> {
  return Builder(root)
      .apply { childBuilder() }
      .__build(parent = null) { it }
}

inline fun <V : Comparable<V>> sortedTreeOf(root: V,
    childBuilder: Builder<V>.() -> Unit): Tree<V> {
  return Builder(root)
      .apply { childBuilder() }
      .__build(parent = null) { children -> children.sortedBy { it.value } }
}

data class Tree<V> constructor(
    val value: V,
    val children: List<Tree<V>>,
    val parent: Tree<V>?
) : List<Tree<V>> by children {

  fun sort(comparator: (left: V, right: V) -> Int): Tree<V> {
    return copy(children = children.sortedWith(
        comparator = Comparator<Tree<V>> { left, right -> comparator(left.value, right.value) }
    ).map { it.sort(comparator) })
  }

  // TODO: For some reason, toString seems broken on this (throws StackOverflowError), even with shallow trees
}

class Builder<V>(val root: V) {

  val __children = mutableListOf<Builder<V>>()

  inline fun node(value: V, childBuilder: Builder<V>.() -> Unit) {
    __children.add(Builder(value).apply { childBuilder() })
  }

  // TODO: Remove this function and make childBuilder optional when we're allowed to have optional inlined-function params
  fun node(value: V) {
    node(value) {}
  }

  fun __build(parent: Tree<V>?,
      modifyChildren: (List<Tree<V>>) -> List<Tree<V>>): Tree<V> {

    val tree = Tree(value = root, children = mutableListOf(), parent = parent)

    // HACK! We had to build the Tree itself before adding any of its children because we need
    // the children to be able to reference their parent (which is this aforementioned Tree...)
    // Good thing Kotlin "immutable" Collections aren't really immutable... :X
    @Suppress("UNCHECKED_CAST")
    (tree.children as MutableList<Tree<V>>)
        .addAll(modifyChildren(__children.map { it.__build(tree, modifyChildren) }))

    return tree
  }
}
