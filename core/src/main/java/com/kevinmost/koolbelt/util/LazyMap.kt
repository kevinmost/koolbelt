package com.kevinmost.koolbelt.util

import java.util.*

inline fun <K, V> lazyMapOf(startingMap: Map<K, V>, crossinline initializer: (K) -> V): Map<K, V> {
  return mutableLazyMapOf(startingMap, initializer)
}

inline fun <K, V> lazyMapOf(crossinline initializer: (K) -> V): Map<K, V> {
  return mutableLazyMapOf(initializer)
}

inline fun <K, V> mutableLazyMapOf(startingMap: Map<K, V>,
    crossinline initializer: (K) -> V)
    : MutableMap<K, V> {
  return object : LazyMap<K, V>(Collections.synchronizedMap(LinkedHashMap(startingMap))) {
    override fun initializer(key: K) = initializer(key)
  }
}

inline fun <K, V> mutableLazyMapOf(crossinline initializer: (K) -> V): MutableMap<K, V> {
  return mutableLazyMapOf(mapOf(), initializer)
}

abstract class LazyMap<K, V>(
    private val delegateMap: MutableMap<K, V> = Collections.synchronizedMap(mutableMapOf())
) : MutableMap<K, V> by delegateMap {

  abstract fun initializer(key: K): V

  override fun get(key: K): V {
    val _v1 = delegateMap
    if (containsKey(key)) {
      return _v1[key]!!
    }
    return synchronized(delegateMap) {
      val _v2 = delegateMap
      if (containsKey(key)) {
        _v2[key]!!
      } else {
        val newValue = initializer(key)
        delegateMap[key] = newValue
        newValue
      }
    }
  }
}
