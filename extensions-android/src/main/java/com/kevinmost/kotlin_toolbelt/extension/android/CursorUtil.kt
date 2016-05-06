package com.kevinmost.kotlin_toolbelt.extension.android

import android.database.Cursor

/**
 * Uses this Cursor until consumed, moving automatically down the rows.
 *
 * @param consumeFunction a function that takes in a cursor and returns the result of one row
 * being consumed. The [consumeFunction] *must* not in any way alter the cursor (such as moving
 * rows or closing it), or this function will fail.
 */
inline fun <T> Cursor?.consumeAll(consumeFunction: (Cursor) -> T): List<T> {
  return this?.use {
    val list = mutableListOf<T>()
    while (this.moveToNext()) {
      list.add(consumeFunction(this))
    }
    return@use list
  } ?: emptyList()
}
