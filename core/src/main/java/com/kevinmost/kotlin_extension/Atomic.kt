package com.kevinmost.kotlin_extension

import java.util.concurrent.atomic.AtomicBoolean
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BooleanAtomic(initialValue: Boolean = false) : ReadWriteProperty<Any, Boolean> {

  private val atomicBoolean = AtomicBoolean(initialValue)

  override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
    return atomicBoolean.get()
  }

  override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
    atomicBoolean.set(value)
  }

}
