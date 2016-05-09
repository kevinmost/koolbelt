package com.kevinmost.kotlin_toolbelt.util

class LazyMap<K, V>(lock: Any? = null, private val initializer: (K) -> V?) {

  @Volatile private var _value: MutableMap<K, Any?> = mutableMapOf()

  // final field is required to enable safe publication of constructed instance
  private val lock = lock ?: this

  operator fun get(key: K): V? {
    val _v1 = _value
    if (_v1.keyIsInitializedValue(key)) {
      @Suppress("UNCHECKED_CAST")
      return _v1[key] as V?
    }
    return synchronized(lock) {
      val _v2 = _value
      if (_v2.keyIsInitializedValue(key)) {
        @Suppress("UNCHECKED_CAST")
        (_v2[key] as V?)
      } else {
        val newValue = initializer(key)
        _value[key] = newValue as Any?
        newValue
      }
    }
  }
}

@Suppress("NOTHING_TO_INLINE")
private inline fun Map<*, *>.keyIsInitializedValue(key: Any?): Boolean {
  return containsKey(key) && get(key) !== UNINITIALIZED_VALUE
}

private object UNINITIALIZED_VALUE
