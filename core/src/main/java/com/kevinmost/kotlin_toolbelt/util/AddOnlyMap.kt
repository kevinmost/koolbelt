package com.kevinmost.kotlin_toolbelt.util

import java.util.LinkedHashMap

fun <K, V> addOnlyMapOf(vararg pairs: Pair<K, V>): AddOnlyMap<K, V> {
  return AddOnlyMapImpl(mutableMapOf(*pairs))
}

interface AddOnlyMap<K, V> : Map<K, V> {
  operator fun set(key: K, value: V)

  operator fun plus(pair: Pair<K, V>): AddOnlyMap<K, V>

  operator fun plus(other: Map<K, V>): AddOnlyMap<K, V>

  fun put(key: K, value: V)
}

/**
 * A Map that is immutable aside from being able to add new keys (no removing allowed).
 */
internal class AddOnlyMapImpl<K, V> internal constructor(private val delegateMap: MutableMap<K, V>)
: AddOnlyMap<K, V>, Map<K, V> by delegateMap {

  override fun set(key: K, value: V) {
    put(key, value)
  }

  override fun plus(pair: Pair<K, V>): AddOnlyMap<K, V> {
    return AddOnlyMapImpl(LinkedHashMap(delegateMap).apply {
      put(pair.first, pair.second)
    })
  }

  override fun plus(other: Map<K, V>): AddOnlyMap<K, V> {
    return AddOnlyMapImpl(LinkedHashMap(delegateMap).apply {
      for ((key, value) in other) {
        put(key, value)
      }
    })
  }

  override fun put(key: K, value: V) {
    delegateMap.put(key, value)
  }
}

