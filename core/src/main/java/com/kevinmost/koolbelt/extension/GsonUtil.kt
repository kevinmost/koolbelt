package com.kevinmost.koolbelt.extension

import com.google.gson.reflect.TypeToken

inline fun <reified T> typeToken(): TypeToken<T> = object : TypeToken<T>() {}