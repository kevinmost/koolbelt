package com.kevinmost.koolbelt.extension

import java.util.*

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
  val currentList = ArrayList<T>(n)
  for (element in this) {
    if (currentList.size == n) {
      block(currentList)
      currentList.clear()
    }
    currentList.add(element)
  }
  if (useLeftoversAtEnd && currentList.isNotEmpty()) {
    block(currentList)
  }
}

/**
 * Collects individual sublists of size [n] from the receiver [Iterable].
 *
 * @param [useLeftoversAtEnd] whether or not to use any remaining elements at the end of the list
 * that do not divide evenly into [n].
 */
fun <T> Iterable<T>.splitEvery(n: Int, useLeftoversAtEnd: Boolean = true): List<List<T>> {
  val result = mutableListOf<MutableList<T>>()

  var currentWorkingList = ArrayList<T>(n)
  for (element in this) {
    if (currentWorkingList.size == n) {
      result.add(currentWorkingList)
      currentWorkingList = ArrayList<T>(n)
    }
    currentWorkingList.add(element)
  }
  if (useLeftoversAtEnd && currentWorkingList.isNotEmpty()) {
    result.add(currentWorkingList)
  }

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
