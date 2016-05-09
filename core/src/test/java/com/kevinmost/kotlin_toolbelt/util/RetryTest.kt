package com.kevinmost.kotlin_toolbelt.util

import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class RetryTest {
  @Test fun `test enough retries to pass`() {
    val flakyNetworkRequest = WorksEveryNTries(3)

    val result = retry<Unit>(4) {
      try {
        flakyNetworkRequest()
        returnSuccess()
      } catch (e: FlakyNetworkException) {
        returnFail()
      }
    }
    assertTrue(result is Result.Success)
  }

  @Test fun `test not enough retries to pass`() {
    val flakyNetworkRequest = WorksEveryNTries(5)

    val result = retry<Unit>(4) {
      try {
        flakyNetworkRequest()
        returnSuccess()
      } catch (e: FlakyNetworkException) {
        returnFail()
      }
    }
    assertTrue(result is Result.Failure)
  }

  @Test fun `test abort`() {
    val result = retry<Unit>(3) {
      returnAbort()
    }

    assertTrue(result is Result.Abort)
  }

  @Test fun `test get current attempt`() {
    retry<Unit>(3) {
      println("There have been $numFailuresSoFar failures trying to make this call")
      returnFail()
    }
  }

  private class WorksEveryNTries(val numTries: Int) {

    var numTimesInvoked = 0

    operator fun invoke() {
      synchronized(this) {
        numTimesInvoked++
        if (numTimesInvoked >= numTries) {
          numTimesInvoked = 0
        } else {
          throw FlakyNetworkException()
        }
      }
    }
  }

  private class FlakyNetworkException : IOException()
}
