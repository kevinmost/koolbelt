package com.kevinmost.kotlin_toolbelt.util

/**
 * Use the DSL methods [RetryContext.returnFail] and [RetryContext.returnSuccess] to mark each run as a success
 * or a failure. Use [RetryContext.returnAbort] to immediately return as a failure.
 */
inline fun <T> retry(numTimes: Int,
    crossinline block: RetryContext<T>.() -> Result<T>)
    : Result<T> {
  val context = RetryContext<T>(numTimes)
  return context.run { block(context) }
}

class RetryContext<T>(private val maxRetries: Int) {

  var numFailuresSoFar = 0
      private set

  /**
   * Mark this run as a failure and try again if we haven't hit [maxRetries]. If this is the final
   * failing run, return [value].
   */
  fun returnFail(value: T? = null): Result.Failure<T> = Result.Failure(value)

  /**
   * Mark this run as a failure and try again if we haven't hit [maxRetries]. If this is the final
   * failing run, return the result of invoking [supplier].
   */
  fun returnFail(supplier: () -> T?): Result.Failure<T> = Result.Failure(supplier())

  /**
   * Immediately return as a failure with this value.
   */
  fun returnAbort(value: T? = null): Result.Abort<T> = Result.Abort(value)

  /**
   * Immediately return as a failure with the result of this function
   */
  fun returnAbort(supplier: () -> T?): Result.Abort<T> = Result.Abort(supplier())

  /**
   * Return this value as a successful value
   */
  fun returnSuccess(value: T? = null): Result.Success<T> = Result.Success(value)

  /**
   * Return the result of this function as a successful value
   */
  fun returnSuccess(supplier: () -> T?): Result.Success<T> = Result.Success(supplier())

  fun run(block: (RetryContext<T>) -> Result<T>): Result<T> {
    var lastFailure: Result.Failure<T>? = null
    while (numFailuresSoFar < maxRetries) {
      val result = block(this)
      when (result) {
        is Result.Success, is Result.Abort -> return result
        is Result.Failure -> {
          numFailuresSoFar++
          lastFailure = result
        }
      }
    }
    return lastFailure ?: Result.Failure<T>(null)
  }
}

sealed class Result<T>(val value: T?) {
  class Success<T> internal constructor(value: T?) : Result<T>(value)
  class Failure<T> internal constructor(failValue: T?) : Result<T>(failValue)
  class Abort<T> internal constructor(abortValue: T?) : Result<T>(abortValue)
}
