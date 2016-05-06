package com.kevinmost.kotlin_toolbelt.extension.android

import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View

inline fun <reified F : Fragment> Fragment.jumpToFragment(
    allowStateLoss: Boolean = false,
    animations: TransitionAnimations = TransitionAnimations(),
    bundle: Bundle = Bundle.EMPTY,
    vararg sharedElementViews: View) {
  (activity as AppCompatActivity).jumpToFragment<F>(
      allowStateLoss,
      animations,
      bundle,
      *sharedElementViews
  )
}

interface HasFragments {
  @IdRes fun getIDOfFragmentFrame(): Int
}

inline fun <reified F : Fragment> AppCompatActivity.jumpToFragment(
    allowStateLoss: Boolean = false,
    animations: TransitionAnimations = TransitionAnimations(),
    bundle: Bundle = Bundle.EMPTY,
    vararg sharedElementViews: View) {

  val replaceID = (this as? HasFragments)?.getIDOfFragmentFrame() ?:
      throw IllegalArgumentException("${this.javaClass.name} is not an instance of ${HasFragments::class.java.name}")
  val newFragmentInstance = F::class.java.newInstance().apply { arguments = bundle }

  supportFragmentManager.beginTransaction()
      .replace(replaceID, newFragmentInstance)
      .apply {
        sharedElementViews.forEach {
          addSharedElement(sharedElementPair(it))
        }
        applyAnimations(animations)
        commit(allowStateLoss)
      }

}

/**
 * @return the identifier of this transaction's back stack entry, if addToBackStack(String) had
 * been called. Otherwise, returns a negative number.
 */
fun FragmentTransaction.commit(allowStateLoss: Boolean = false): Int {
  return if (allowStateLoss) {
    commitAllowingStateLoss()
  } else {
    commit()
  }
}

fun FragmentTransaction.addSharedElement(sharedElement: Pair<View, String>): FragmentTransaction {
  return addSharedElement(sharedElement.first, sharedElement.second)
}

fun FragmentTransaction.applyAnimations(animations: TransitionAnimations): FragmentTransaction {
  return setCustomAnimations(
      animations.enterAnimation,
      animations.exitAnimation,
      animations.enterPopAnimation,
      animations.exitPopAnimation)
}

data class TransitionAnimations(
    @AnimRes val enterAnimation: Int = 0,
    @AnimRes val exitAnimation: Int = 0,
    @AnimRes val enterPopAnimation: Int = 0,
    @AnimRes val exitPopAnimation: Int = 0
)
