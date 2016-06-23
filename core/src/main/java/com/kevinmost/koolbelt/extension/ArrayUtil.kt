package com.kevinmost.koolbelt.extension

// There's no stdlib method that doesn't return a new list. We want to keep this as an array for
// the sake of efficiency sometimes.
inline fun <T, reified R> Array<out T>.mapToArray(transform: (T) -> R): Array<R> {
  val result = arrayOfNulls<R>(this.size)
  for (i in 0..size - 1) {
    result[i] = transform(this[i])
  }
  @Suppress("CAST_NEVER_SUCCEEDS") // yes it does
  return result as Array<R>
}

operator inline fun <reified T> Array<T>.get(range: IntRange): Array<T> {
  return if (range.step == 1 && !range.isEmpty()) {
    copyOfRange(range.start, range.endInclusive)
  } else {
    toList()[range].toTypedArray()
  }
}

inline fun <T, reified R> mapEach(vararg elements: T, mapFunction: (T) -> R): Array<R> {
  return elements.mapToArray(mapFunction)
}
