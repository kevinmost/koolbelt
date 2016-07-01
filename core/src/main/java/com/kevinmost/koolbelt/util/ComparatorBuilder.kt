package com.kevinmost.koolbelt.util

import java.util.*
import kotlin.comparisons.compareValues

fun <T> buildComparator(init: ComparatorBuilder<T>.() -> Unit):Comparator<T> {
  return ComparatorBuilder<T>().apply { init() }.build()
}


class ComparatorBuilder<T> internal constructor() {

  private val comparisons = mutableListOf<Pair<(T) -> Comparable<*>?, Boolean>>()

  fun withComparison(descending: Boolean = false, selector: (T) -> Comparable<*>?) {
    comparisons += selector to descending
  }

  internal fun build(): Comparator<T> {
    return Comparator { left, right ->
      for ((comparison, descending) in comparisons) {
        val comparisonResult = compareValues(comparison(left), comparison(right))
        if (comparisonResult != 0) {
          return@Comparator if (descending) -comparisonResult else comparisonResult
        }
      }
      return@Comparator 0
    }
  }
}
