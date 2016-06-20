package com.kevinmost.koolbelt.extension

import java.util.*

fun <T> Optional<T>?.unwrap(): T? {
  return this?.orElse(null)
}

