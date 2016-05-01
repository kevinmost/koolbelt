package com.kevinmost.kotlin_extension.android

import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View

inline fun <reified F : Fragment> Fragment.jumpTo(
    allowStateLoss: Boolean = false,
    @AnimRes enterAnimation: Int = 0,
    @AnimRes exitAnimation: Int = 0,
    @AnimRes enterPopAnimation: Int = 0,
    @AnimRes exitPopAnimation: Int = 0,
    bundle: Bundle = Bundle(),
    vararg sharedElementViews: View) {
  val newFragmentInstance = F::class.java.newInstance().apply { arguments = bundle }
  fragmentManager.beginTransaction()
      .add(newFragmentInstance, F::class.java.name)
      .apply {
        sharedElementViews.forEach {
          addSharedElement(it to (it.tag as String))
        }
        setCustomAnimations(enterAnimation, exitAnimation, enterPopAnimation, exitPopAnimation)
        if (allowStateLoss) {
          commitAllowingStateLoss()
        } else {
          commit()
        }
      }
}

fun FragmentTransaction.addSharedElement(sharedElement: Pair<View, String>): FragmentTransaction {
  return addSharedElement(sharedElement.first, sharedElement.second)
}
