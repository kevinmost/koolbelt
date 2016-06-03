package com.kevinmost.kotlin_toolbelt.extension

import java.util.Optional

fun <T> Optional<T>?.unwrap(): T? {
  return this?.orElse(null)
}

