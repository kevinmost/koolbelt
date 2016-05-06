package com.kevinmost.kotlin_toolbelt.util

import com.kevinmost.kotlin_toolbelt.util.weakReference
import org.junit.Test

// TODO: Not really sure how to test that it actually gets GC'd...
class WeakRefDelegateTest {
  var actual: Int? = 1

  val weak by weakReference(actual)

  @Test fun `test weak reference can be retrieved`() {
    assert(weak == 1)
  }
}