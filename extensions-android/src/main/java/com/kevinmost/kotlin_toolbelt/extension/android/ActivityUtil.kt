package com.kevinmost.kotlin_toolbelt.extension.android

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kevinmost.kotlin_toolbelt.extension.javaClass

fun activityCallbacks(
    onCreate: (Activity, savedInstanceState: Bundle?) -> Unit = { activity, savedInstanceState -> },
    onStart: (Activity) -> Unit = {},
    onResume: (Activity) -> Unit = {},
    onPause: (Activity) -> Unit = {},
    onStop: (Activity) -> Unit = {},
    onSaveInstanceState: (Activity, outState: Bundle?) -> Unit = { activity, outState -> },
    onDestroy: (Activity) -> Unit = {}
): Application.ActivityLifecycleCallbacks {
  return object : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
      onCreate(activity, savedInstanceState)
    }

    override fun onActivityStarted(activity: Activity) {
      onStart(activity)
    }

    override fun onActivityResumed(activity: Activity) {
      onResume(activity)
    }

    override fun onActivityPaused(activity: Activity) {
      onPause(activity)
    }

    override fun onActivityStopped(activity: Activity) {
      onStop(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
      onSaveInstanceState(activity, outState)
    }

    override fun onActivityDestroyed(activity: Activity) {
      onDestroy(activity)
    }
  }
}

/**
 * Put this on any [AppCompatActivity] to be allowed to use [intentWithTypeSafeArg] to create an
 * Intent that bundles type-safe data to it, and [getTypeSafeArg] within the Activity to retrieve
 * that data.
 */
interface ExpectsBundleArgs<ARG : Any>

inline fun <reified A, ARG> Context.intentWithTypeSafeArg(
    arg: ARG,
    vararg otherArgs: Pair<String, Any>
): Intent where
    ARG : Any,
    A : AppCompatActivity,
    A : ExpectsBundleArgs<ARG> {
  return Intent(this, A::class.java).apply {
    putExtras(Bundle().put("TYPE_SAFE_ARG" to arg, *otherArgs))
  }
}

inline fun <reified ARG : Any> ExpectsBundleArgs<ARG>.getTypeSafeArg(): ARG {
  if (this !is AppCompatActivity) {
    throw IllegalArgumentException("Only Activities can implement ${ARG::class.javaClass()?.name}")
  }
  return intent?.extras?.get("TYPE_SAFE_ARG") as? ARG ?:
      throw IllegalArgumentException("A type-safe argument wasn't passed to this activity, ${javaClass.name}")
}
