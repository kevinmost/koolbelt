package com.kevinmost.kotlin_toolbelt.extension

import org.junit.Test

class AnyUtilTest {
  @Test fun `test javaClass() method`() {
    assert("".javaClass()?.simpleName == "String")
    assert(1.javaClass()?.simpleName == "Integer")
    assert(null.javaClass()?.simpleName?.equals("null") ?: true)
  }
}
