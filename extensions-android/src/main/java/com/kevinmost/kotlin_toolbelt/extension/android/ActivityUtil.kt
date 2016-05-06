package com.kevinmost.kotlin_toolbelt.extension.android

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kevinmost.kotlin_toolbelt.extension.javaClass

fun activityCallbacks(init: ActivityCallbacks.() -> Unit): Application.ActivityLifecycleCallbacks {
  return ActivityCallbacks().apply { init() }.actualCallback
}

class ActivityCallbacks internal constructor() {
  internal val actualCallback = object : ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) =
        onCreate(activity, savedInstanceState)

    override fun onActivityStarted(activity: Activity) = onStart(activity)

    override fun onActivityResumed(activity: Activity) = onResume(activity)

    override fun onActivityPaused(activity: Activity) = onPause(activity)

    override fun onActivityStopped(activity: Activity) = onStop(activity)

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) =
        onSaveInstanceState(activity, outState)

    override fun onActivityDestroyed(activity: Activity) = onDestroy(activity)
  }

  private var onCreate: (Activity, savedInstanceState: Bundle?) -> Unit = { activity, savedInstanceState -> }
  private var onStart: (Activity) -> Unit = {}
  private var onResume: (Activity) -> Unit = {}
  private var onPause: (Activity) -> Unit = {}
  private var onStop: (Activity) -> Unit = {}
  private var onSaveInstanceState: (Activity, outState: Bundle?) -> Unit = { activity, outState -> }
  private var onDestroy: (Activity) -> Unit = {}

  fun onCreate(onCreate: (Activity, savedInstanceState: Bundle?) -> Unit) {
    this.onCreate = onCreate
  }

  fun onStart(onStart: (Activity) -> Unit) {
    this.onStart = onStart
  }

  fun onResume(onResume: (Activity) -> Unit) {
    this.onResume = onResume
  }

  fun onPause(onPause: (Activity) -> Unit) {
    this.onPause = onPause
  }

  fun onStop(onStop: (Activity) -> Unit) {
    this.onStop = onStop
  }

  fun onSaveInstanceState(onSaveInstanceState: (Activity, outState: Bundle?) -> Unit) {
    this.onSaveInstanceState = onSaveInstanceState
  }

  fun onDestroy(onDestroy: (Activity) -> Unit) {
    this.onDestroy = onDestroy
  }
}

/**
 * Put this on any [Activity] to be allowed to use [intentWithTypeSafeArg] to create an
 * Intent that bundles type-safe data to it, and [getTypeSafeArg] within the Activity to retrieve
 * that data.
 */
interface ExpectsBundleArgs<ARG : Any>

inline fun <reified A, ARG> Context.intentWithTypeSafeArg(
    arg: ARG,
    vararg otherArgs: Pair<String, Any>
): Intent where
    ARG : Any,
    A : Activity,
    A : ExpectsBundleArgs<ARG> {
  return Intent(this, A::class.java).apply {
    putExtras(Bundle().put("TYPE_SAFE_ARG" to arg, *otherArgs))
  }
}

inline fun <reified ARG : Any> ExpectsBundleArgs<ARG>.getTypeSafeArg(): ARG {
  if (this !is Activity) {
    throw IllegalArgumentException("Only Activities can implement ${ARG::class.javaClass()?.name}")
  }
  return intent?.extras?.get("TYPE_SAFE_ARG") as? ARG ?:
      throw IllegalArgumentException(
          "A type-safe argument wasn't passed to this activity, ${javaClass.name}")
}
