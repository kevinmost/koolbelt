package com.kevinmost.koolbelt.util

inline fun <E> buildList(init: CollectionBuilder.ListBuilder<E>.() -> Unit): List<E> {
  return CollectionBuilder.ListBuilder<E>().apply { init() }.__build()
}

inline fun <E> buildSet(init: CollectionBuilder.ListBuilder<E>.() -> Unit): Set<E> {
  return CollectionBuilder.ListBuilder<E>().apply { init() }.__build().toSet()
}

inline fun <K, V> buildMap(init: CollectionBuilder.MapBuilder<K, V>.() -> Unit): Map<K, V> {
  return CollectionBuilder.MapBuilder<K, V>().apply { init() }.__build()
}

sealed class CollectionBuilder<E, OUT> {
  val entries = mutableListOf<E>()

  fun addIf(predicate: Boolean, element: E) {
    addIf(predicate) { element }
  }

  inline fun addIf(predicate: Boolean, element: () -> E) {
    if (predicate) {
      entries += element()
    }
  }

  abstract fun __build(): OUT

  class ListBuilder<V>: CollectionBuilder<V, List<V>>() {
    override fun __build(): List<V> = entries

    fun addAllIf(predicate: Boolean, elements: Iterable<V>) {
      addAllIf(predicate) { elements }
    }

    inline fun addAllIf(predicate: Boolean, elements: () -> Iterable<V>) {
      if (predicate) {
        entries += elements()
      }
    }
  }

  class MapBuilder<K, V> : CollectionBuilder<Pair<K, V>, Map<K, V>>() {
    override fun __build(): Map<K, V> = entries.toMap()

    fun addAllIf(predicate: Boolean, elements: Map<K, V>) {
      addAllIf(predicate) { elements }
    }

    inline fun addAllIf(predicate: Boolean, elements: () -> Map<K, V>) {
      if (predicate) {
        entries += elements().map { it.toPair() }
      }
    }
  }
}

