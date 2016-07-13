package com.kevinmost.koolbelt.extension.android

import android.database.Cursor

/**
 * Uses this Cursor until consumed, moving automatically down the rows.
 *
 * @param consumeFunction a function that takes in a cursor and returns the result of one row
 * being consumed. The [consumeFunction] *must* not in any way alter the cursor (such as moving
 * rows or closing it), or this function will fail.
 */
inline fun <T: Any> Cursor?.consumeAll(crossinline consumeFunction: (Cursor) -> T): List<T> {
  return toSequence(consumeFunction).toList()
}

/**
 * Uses this Cursor until consumed, moving automatically down the rows.
 *
 * @param consumeFunction a function that takes in a cursor and returns the result of one row
 * being consumed. The [consumeFunction] *must* not in any way alter the cursor (such as moving
 * rows or closing it), or this function will fail.
 */
inline fun <T : Any> Cursor?.toSequence(crossinline consumeFunction: (Cursor) -> T): Sequence<T> {
  if (this == null || !this.moveToFirst()) return emptySequence()

  this.use {
    return generateSequence {
      return@generateSequence if (this.isClosed || this.isAfterLast) {
        null
      } else {
        consumeFunction(this).apply {
          this@toSequence.moveToNext()
        }
      }
    }
  }
}
