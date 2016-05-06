package com.kevinmost.kotlin_extension

fun <K, V> Map<K?, V>.filterNotNullKeys(): Map<K, V> {
  return this
      .filterKeys { it != null }
      .mapKeys { it.key!! } // Silly that we have to assert the keys aren't null, but Kotlin can't tell from the above filter
}

