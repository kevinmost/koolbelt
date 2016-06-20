package com.kevinmost.koolbelt.extension.android

import android.content.Context
import android.support.annotation.PluralsRes

fun Context.getPlural(@PluralsRes id: Int,
    quantity: Int,
    vararg args: Any = arrayOf(quantity))
    : CharSequence {
  return resources.getQuantityString(id, quantity, *args)
}
