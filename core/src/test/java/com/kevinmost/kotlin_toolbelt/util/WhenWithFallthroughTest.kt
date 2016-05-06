package com.kevinmost.kotlin_toolbelt.util

import org.junit.Test

class WhenWithFallthroughTest {
  @Test fun `test breakAfter works`() {
    val casesHit = mutableSetOf<Int>()
    whenWithFallthrough(4) {
      case(2) { casesHit += 0 }
      case(5) { casesHit += 1 }

      case(predicate = { it < 6 }) {
        casesHit += 2
      }

      case(predicate = { it % 2 == 1 }, breakAfter = true) {
        casesHit += 3
      }

      default {
        casesHit += 4
      }
    }
    assert(!casesHit.contains(0))
    assert(!casesHit.contains(1))
    assert(casesHit.contains(2))
    assert(casesHit.contains(3))
    assert(!casesHit.contains(4))
  }
  
  @Test fun `test default hit when nothing else is`() {
    val casesHit = mutableSetOf<Int>()
    whenWithFallthrough(1) {
      case(2) { casesHit += 0 }
      case(5) { casesHit += 1 }

      case(predicate = { it > 6 }) {
        casesHit += 2
      }

      default { casesHit += 3 }
    }
    assert(!casesHit.contains(0))
    assert(!casesHit.contains(1))
    assert(!casesHit.contains(2))
    assert(casesHit.contains(3))
  }
}
