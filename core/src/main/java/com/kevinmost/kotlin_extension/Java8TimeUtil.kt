package com.kevinmost.kotlin_extension

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Instant.localDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(this, ZoneId.systemDefault())
}
