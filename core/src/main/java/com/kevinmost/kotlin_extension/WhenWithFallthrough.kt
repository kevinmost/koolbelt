package com.kevinmost.kotlin_extension

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
        var haveAnyMatched = false
        for (case in cases) {
            if (case.matches(receiver)) {
                haveAnyMatched = true
                case.ifMatches(receiver)
                if (case.breakAfter) {
                    break
                }
            }
        }
        if (!haveAnyMatched) {
            defaultBlock?.invoke(receiver)
        }
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
): Match<T> {
    override fun matches(test: T) = toMatch == test
}

internal data class PredicateMatch<T>(
        val predicate: (T) -> Boolean,
        override val breakAfter: Boolean,
        override val ifMatches: (T) -> Unit
): Match<T> {
    override fun matches(test: T) = predicate(test)

}