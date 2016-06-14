package com.kevinmost.kotlin_toolbelt.extension

import java.util.ArrayList

fun <T> MutableList<T>.setOrAppend(index: Int, element: T): MutableList<T> {
  if (size > index) {
    set(index, element)
  } else {
    add(index, element)
  }
  return this
}

fun <T> mutableListOfSize(startingSize: Int) : List<T> = ArrayList(startingSize)
