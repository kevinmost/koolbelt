package com.kevinmost.koolbelt.util

import java.lang.ref.WeakReference
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T> weakReference(referent: T): ReadOnlyProperty<Any, T?> = WeakRefDelegate(referent)

private class WeakRefDelegate<T>(referent: T) : ReadOnlyProperty<Any, T?> {

  private val weakReference = WeakReference(referent)

  override fun getValue(thisRef: Any, property: KProperty<*>): T? {
    return weakReference.get()
  }
}

