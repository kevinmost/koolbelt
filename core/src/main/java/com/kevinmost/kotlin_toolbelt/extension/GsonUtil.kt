package com.kevinmost.kotlin_toolbelt.extension

import com.google.gson.reflect.TypeToken

inline fun <reified T> typeToken(): TypeToken<T> = object: TypeToken<T>() {}