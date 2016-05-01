package com.kevinmost.kotlin_extension

inline fun <reified T> Class<*>.getStaticField(staticFieldName: String): T? {
  return getField(staticFieldName).get(null) as T?
}
