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

operator fun <T> List<T>.get(range: IntProgression): List<T> {
  return if (range.isEmpty()) {
    emptyList()
  } else {
    if (range.step == 1) {
      subList(range.first, range.last)
    } else {
      range.fold(mutableListOf<T>()) { list, number ->
        list.apply { add(this@get[number]) }
      }
    }
  }
}

fun <T> mutableListOfSize(startingSize: Int): MutableList<T> = ArrayList(startingSize)
