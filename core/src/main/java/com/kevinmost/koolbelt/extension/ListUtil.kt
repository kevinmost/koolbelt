package com.kevinmost.koolbelt.extension

import java.util.*

fun <T> MutableList<T>.setOrAppend(index: Int, element: T): MutableList<T> {
  if (size > index) {
    set(index, element)
  } else {
    add(index, element)
  }
  return this
}

operator fun <T> List<T>.get(range: IntRange): List<T> {
  return subList(range.start, range.endInclusive)
}

fun <T> mutableListOfSize(startingSize: Int): MutableList<T> = ArrayList(startingSize)
