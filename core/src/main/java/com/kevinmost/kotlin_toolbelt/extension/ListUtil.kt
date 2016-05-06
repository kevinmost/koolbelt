package com.kevinmost.kotlin_toolbelt.extension

fun <T> MutableList<T>.setOrAppend(index: Int, element: T): MutableList<T> {
  if (size > index) {
    set(index, element)
  } else {
    add(index, element)
  }
  return this
}
