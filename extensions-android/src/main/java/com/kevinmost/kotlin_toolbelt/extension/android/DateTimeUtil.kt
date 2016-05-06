package com.kevinmost.kotlin_toolbelt.extension

import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

fun Instant.localDateTime(): LocalDateTime {
  return LocalDateTime.ofInstant(this, ZoneId.systemDefault())
}
