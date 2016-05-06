package com.kevinmost.util

import com.kevinmost.kotlin_extension.mapToArray
import java.util.ArrayList
import java.util.LinkedHashMap

fun <K, V> mergingListsMapOf(vararg pairs: Pair<K, List<V>>): MergingListsMap<K, V> {
  // IntelliJ just flips out when it sees the AddOnlyMap class...
  @Suppress("RemoveExplicitTypeArguments")
  return MergingListsMapImpl<K, V>(
      mutableMapOf(*pairs.mapToArray { it.first to maybeToMutableList(it.second) }))
}

interface MergingListsMap<K, V> : AddOnlyMap<K, List<out V>> {
  override operator fun plus(pair: Pair<K, List<out V>>): MergingListsMap<K, V>

  override operator fun plus(other: Map<K, List<out V>>): MergingListsMap<K, V>
}

private class MergingListsMapImpl<K, V> internal constructor(private val delegateMap: MutableMap<K, MutableList<V>>)
: MergingListsMap<K, V>, Map<K, List<V>> by delegateMap {

  override fun set(key: K, value: List<V>) {
    put(key, value)
  }

  override fun plus(pair: Pair<K, List<V>>): MergingListsMap<K, V> {
    return MergingListsMapImpl(LinkedHashMap(delegateMap)).apply {
      put(pair.first, pair.second)
    }
  }

  override fun plus(other: Map<K, List<V>>): MergingListsMap<K, V> {
    return MergingListsMapImpl(LinkedHashMap(delegateMap)).apply {
      for((key, value) in other) {
        put(key, value)
      }
    }
  }

  override fun put(key: K, value: List<V>) {
    val currentValue = get(key)
    if (currentValue == null) {
      delegateMap.put(key, maybeToMutableList(value))
      return
    }
    delegateMap.put(key, ArrayList(currentValue + value))
  }
}

internal fun <V> maybeToMutableList(list: List<V>): MutableList<V> {
  return if (list is MutableList<V>) list else list.toMutableList()
}
