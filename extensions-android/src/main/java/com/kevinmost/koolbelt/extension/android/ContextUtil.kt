package com.kevinmost.koolbelt.extension.android

import android.content.Context
import android.support.annotation.PluralsRes

fun Context.getPlural(@PluralsRes id: Int,
    quantity: Int,
    vararg args: Any = arrayOf(quantity))
    : CharSequence {
  return resources.getQuantityString(id, quantity, *args)
}

inline fun Context.dimensions(block: DimensionsContext.() -> Unit) {
  DimensionsContext(this).apply(block)
}

class DimensionsContext(private val context: Context) {

  val Int.dp: Int
    get() = (this * density).toInt()

  val Int.sp: Int
    get() = (this * scaledDensity).toInt()

  val Int.px2Dp: Float
    get() = (this.toFloat() / density).toFloat()

  val Int.px2Sp: Float
    get() = (this.toFloat() / scaledDensity).toFloat()


  private val density: Float
    get() = context.resources.displayMetrics.density

  private val scaledDensity: Float
    get() = context.resources.displayMetrics.scaledDensity
}
