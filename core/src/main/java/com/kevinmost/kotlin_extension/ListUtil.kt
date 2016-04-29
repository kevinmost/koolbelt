package com.kevinmost.kotlin_extension

fun <T> MutableList<T>.setOrAppend(index: Int, element: T) {
    if (size > index) {
        set(index, element)
    } else {
        add(index, element)
    }
}
