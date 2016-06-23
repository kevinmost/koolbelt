package com.kevinmost.koolbelt.extension

/**
 * Invokes [block] upon each individual sublist that is chunked out from every [n] elements in this
 * [Iterable].
 *
 * @param [useLeftoversAtEnd] whether or not to use any remaining elements at the end of the list
 * that do not divide evenly into [n].
 */
@JvmOverloads
inline fun <T> Iterable<T>.every(
    n: Int,
    useLeftoversAtEnd: Boolean = true,
    block: (List<T>) -> Unit) {
  var currentList = mutableListOfSize<T>(n)
  for (element in this) {
    if (currentList.size == n) {
      block(currentList)
      currentList = mutableListOfSize(n)
    }
    currentList.add(element)
  }
  if (useLeftoversAtEnd && currentList.isNotEmpty()) {
    block(currentList)
  }
}

// Optimized version of the above function that lets you take sublists instead of iterating through
@JvmOverloads
inline fun <T> List<T>.every(
    n: Int,
    useLeftoversAtEnd: Boolean = true,
    block: (List<T>) -> Unit) {

  var currentStartingIndex = 0
  var currentEndingIndex = n
  while (currentEndingIndex < size) {
    block(this[currentStartingIndex..currentEndingIndex])
    currentStartingIndex += n
    currentEndingIndex += n
  }
  if (useLeftoversAtEnd) {
    val leftovers = this[currentStartingIndex..size]
    if (leftovers.isNotEmpty()) {
      block(leftovers)
    }
  }
}

/**
 * Collects individual sublists of size [n] from the receiver [Iterable].
 *
 * @param [useLeftoversAtEnd] whether or not to use any remaining elements at the end of the list
 * that do not divide evenly into [n].
 */
@JvmOverloads
fun <T> Iterable<T>.splitEvery(n: Int, useLeftoversAtEnd: Boolean = true): List<List<T>> {
  val result = mutableListOf<List<T>>()
  every(n = n, useLeftoversAtEnd = useLeftoversAtEnd) { result.add(it) }
  return result
}

// Optimized version of the above function that lets you take sublists instead of iterating through
@JvmOverloads
fun <T> List<T>.splitEvery(n: Int, useLeftoversAtEnd: Boolean = true): List<List<T>> {
  val result = mutableListOf<List<T>>()
  every(n = n, useLeftoversAtEnd = useLeftoversAtEnd) { result.add(it) }
  return result
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
