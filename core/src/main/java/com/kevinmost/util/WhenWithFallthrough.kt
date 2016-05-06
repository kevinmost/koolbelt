package com.kevinmost.util

fun <T> whenWithFallthrough(receiver: T, block: WhenWithFallthrough<T>.() -> Unit) {
  WhenWithFallthrough<T>()
      .apply { block(this) }
      .exec(receiver)
}

class WhenWithFallthrough<T>() {
  private val cases = arrayListOf<Match<T>>()
  private var defaultBlock: ((T) -> Unit)? = null

  fun case(predicate: (T) -> Boolean, breakAfter: Boolean = false, ifMatches: (T) -> Unit) {
    cases += PredicateMatch(predicate, breakAfter, ifMatches)
  }

  fun case(toMatch: T, breakAfter: Boolean = false, ifMatches: (T) -> Unit) {
    cases += EqualsMatch(toMatch, breakAfter, ifMatches)
  }

  fun default(block: (T) -> Unit) {
    defaultBlock = block
  }

  internal fun exec(receiver: T) {
    val firstMatch = cases.indexOfFirst { it.matches(receiver) }

    if (firstMatch > -1) {
      for (index in firstMatch..cases.count() - 1) {
        cases[index].ifMatches(receiver)
        if (cases[index].breakAfter) {
          return
        }
      }
    }
    defaultBlock?.invoke(receiver)
  }
}

internal interface Match<T> {
  fun matches(test: T): Boolean
  val breakAfter: Boolean
  val ifMatches: (T) -> Unit
}

internal data class EqualsMatch<T>(
    val toMatch: T,
    override val breakAfter: Boolean,
    override val ifMatches: (T) -> Unit
) : Match<T> {
  override fun matches(test: T) = toMatch == test
}

internal data class PredicateMatch<T>(
    val predicate: (T) -> Boolean,
    override val breakAfter: Boolean,
    override val ifMatches: (T) -> Unit
) : Match<T> {
  override fun matches(test: T) = predicate(test)
}