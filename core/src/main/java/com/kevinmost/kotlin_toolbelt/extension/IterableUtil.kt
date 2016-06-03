package com.kevinmost.kotlin_toolbelt.extension

fun <T> Iterable<T>.splitEvery(n: Int): List<List<T>> {
  return splitWhen { index, element, currentSubList -> index % n == 0 }
}

inline fun <T> Iterable<T>.splitWhen(
    splitPredicate: (index: Int, element: T, currentSubList: List<T>) -> Boolean
): List<List<T>> {
  val result = mutableListOf<MutableList<T>>()
  forEachIndexed { index, element ->
    if (index == 0 || splitPredicate(index, element, result.last())) {
      result.add(mutableListOf<T>())
    }
    result.last().add(element)
  }
  return result
}
