package com.kevinmost.kotlin_toolbelt.util

inline fun <R> measureTimeMillis(block: () -> R): TimedResult<R> {
  val start = System.currentTimeMillis()
  return TimedResult(block(), System.currentTimeMillis() - start)
}

inline fun <R> measureNanoTime(block: () -> R): TimedResult<R> {
  val start = System.nanoTime()
  return TimedResult(block(), System.nanoTime() - start)
}

data class TimedResult<T>(val data: T, val timeInNs: Long)
