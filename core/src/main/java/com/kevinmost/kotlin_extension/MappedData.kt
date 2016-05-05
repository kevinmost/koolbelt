package com.kevinmost.kotlin_extension

/**
 * Just a cleaner way to store a Map<K, List<V>>, to which data can be added, but not removed
 */
class MappedData<K, V>(vararg initialValues: Pair<K, V>) {

  private val data: MutableMap<K, MutableList<V>>

  init {
    val pairs: Array<Pair<K, MutableList<V>>> =
        initialValues.mapToArray { it.first to mutableListOf(it.second) }
    this.data = mutableMapOf(*pairs)
  }

  fun getMappedData(key: K): List<V> {
    return data.getOrElse(key) { listOf() }
  }

  operator fun get(key: K): List<V> {
    return data.getOrPut(key) { mutableListOf() }
  }

  operator fun set(key: K, value: V) {
    data.getOrPut(key) { mutableListOf() }.add(value)
  }
}
