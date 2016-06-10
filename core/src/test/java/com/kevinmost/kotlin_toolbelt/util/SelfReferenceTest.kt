package com.kevinmost.kotlin_toolbelt.util

import org.junit.Assert
import org.junit.Test

class SelfReferenceTest {
  @Test fun `test self references work`() {
    selfReference<FooClass> {
      FooClass(5) {
        Assert.assertEquals(5, self.someInt)
      }
    }.useSomeIntFunction()
  }
}

class FooClass(val someInt: Int, val useSomeIntFunction: () -> Unit) {
}

