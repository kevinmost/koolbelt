package com.kevinmost.koolbelt.extension

fun <K, V : Any?> Map<K?, V>.filterNotNullKeys(): Map<K, V> {
  @Suppress("UNCHECKED_CAST")
  return (this.filterKeys { it != null }) as Map<K, V>
}

fun <K : Any?, V> Map<K, V?>.filterNotNullValues(): Map<K, V> {
  @Suppress("UNCHECKED_CAST")
  return (this.filterValues { it != null }) as Map<K, V>
}

fun <K, V> Map<K?, V?>.filterNotNullKeysAndValues(): Map<K, V> {
  return this.filterNotNullKeys().filterNotNullValues()
}
