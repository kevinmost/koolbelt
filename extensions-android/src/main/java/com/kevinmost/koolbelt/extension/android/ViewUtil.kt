package com.kevinmost.koolbelt.extension.android

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.TypedArray
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.StyleableRes
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.MeasureSpec
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import java.util.*


fun <T : View> Activity.find(@IdRes id: Int): T? {
  @Suppress("UNCHECKED_CAST")
  return findViewById(id) as? T
}

fun <T : View> View.find(@IdRes id: Int): T? {
  @Suppress("UNCHECKED_CAST")
  return findViewById(id) as? T
}

fun <T : View> Dialog.find(@IdRes id: Int): T? {
  @Suppress("UNCHECKED_CAST")
  return findViewById(id) as? T
}

fun ViewGroup.getChildren(): List<View> {
  val list = ArrayList<View>(childCount)
  for (index in 0..childCount) {
    list.add(getChildAt(index))
  }
  return list
}

inline fun ViewGroup.forEachChild(forEachChild: View.() -> Unit) {
  for (index in 0..childCount) {
    getChildAt(index)?.let {
      forEachChild(it)
    }
  }
}

/**
 * @return this view if it of type [T]; otherwise, the first child of this view that is of type [T].
 * The search is performed depth-first.
 */
fun <T : View> View.getChildOfType(type: Class<T>): T? {
  if (type.isInstance(this)) {
    return type.cast(this)
  }
  if (this is ViewGroup) {
    for (index in 0..childCount) {
      getChildAt(index).getChildOfType(type)?.let { return it }
    }
  }
  return null
}

/**
 * @return a list of all children of this View that are of type [T]. This View is also included in
 * the list as the first element if it is of type [T]. The search is performed depth-first.
 */
fun <T : View?> View.getAllChildrenOfType(type: Class<T>): List<T> {
  val list = mutableListOf<T>()
  if (type.isInstance(this)) {
    list.add(type.cast(this))
  }
  if (this is ViewGroup) {
    forEachChild {
      list.addAll(getAllChildrenOfType(type))
    }
  }
  return list.toList() // Return immutable collection
}

fun ViewGroup.addView(@LayoutRes layoutRes: Int, index: Int = -1) {
  val view: View = context.inflate(layoutRes, this, false)
  this.addView(view, index)
}

@JvmOverloads
fun <T : View> Context.inflate(
    @LayoutRes layout: Int,
    into: ViewGroup? = null,
    attachToParent: Boolean = false
): T {
  @Suppress("UNCHECKED_CAST")
  return LayoutInflater.from(this).inflate(layout, into, into != null && attachToParent) as T
}

fun sharedElementPair(view: View): Pair<View, String> {
  view.tag?.let { tag ->
    return view to tag as String
  } ?: throw IllegalArgumentException(
      "Your view must have a tag set to build this shared element transition!")
}

inline fun Context.styledAttributes(attributeSet: AttributeSet,
    @StyleableRes attrs: IntArray,
    block: TypedArray.() -> Unit) {
  val typedArray = obtainStyledAttributes(attributeSet, attrs)
  block(typedArray)
  try {
    typedArray.recycle()
  } catch(ignored: RuntimeException) {
    // This is thrown if we call .recycle() twice. The user doesn't have to call this method, but maybe they will!
  }
}

fun copyMeasureSpec(
    oldMeasureSpec: Int = -1,
    newSize: Int = -1,
    newMode: MeasureSpecMode? = null): Int {
  if (oldMeasureSpec == -1) {
    return MeasureSpec.makeMeasureSpec(newSize, newMode?.modeInt ?: MeasureSpec.UNSPECIFIED)
  }
  val oldSize = MeasureSpec.getSize(oldMeasureSpec)
  val oldMode = MeasureSpec.getMode(oldMeasureSpec)
  return MeasureSpec.makeMeasureSpec(
      if (newSize == -1) oldSize else newSize,
      if (newMode == null) oldMode else newMode.modeInt
  )
}

enum class MeasureSpecMode(val modeInt: Int) {
  EXACTLY(MeasureSpec.EXACTLY),
  AT_MOST(MeasureSpec.AT_MOST),
  UNSPECIFIED(MeasureSpec.UNSPECIFIED),
  ;

  operator fun get(size: Int): Int {
    return MeasureSpec.makeMeasureSpec(size, modeInt)
  }
}

/**
 * @param whileDisabled A function that is invoked after disabling the receiver [View]. If this
 * function returns `true`, the receiver [View] will be re-enabled.
 *
 * @return the return value of [whileDisabled]; essentially, whether or not this [View] will be
 * re-enabled at the end.
 */
inline fun <T : View> T.disableWhile(whileDisabled: (T) -> Boolean): Boolean {
  isEnabled = false
  whileDisabled(this).let { result ->
    if (result) {
      isEnabled = true
    }
    return result
  }
}

@JvmOverloads
fun View.snackbar(
    message: CharSequence,
    @IdRes anchorView: Int = View.NO_ID,
    duration: Int = Snackbar.LENGTH_LONG): Snackbar {
  return Snackbar.make(
      if (anchorView == View.NO_ID) this else findViewById(anchorView)!!,
      message,
      duration
  ).apply { show() }
}

fun Context.toast(
    message: CharSequence,
    duration: Int = Toast.LENGTH_LONG) {
  Toast.makeText(this, message, duration).show()
}

sealed class Visibility {

  interface HiddenVisibility

  object VISIBLE : Visibility()
  object INVISIBLE : Visibility(), HiddenVisibility
  object GONE : Visibility(), HiddenVisibility
}

var View.vis: Visibility
  get() {
    return when(visibility) {
      VISIBLE -> Visibility.VISIBLE
      INVISIBLE -> Visibility.INVISIBLE
      GONE -> Visibility.GONE
      else -> throw IllegalStateException("A view's visibility cannot be anything but VISIBLE, INVISIBLE, or GONE. The int value for this view's visibility was $visibility")
    }
  }
  set(value) {
    visibility = when(value) {
      Visibility.VISIBLE -> VISIBLE
      Visibility.INVISIBLE -> INVISIBLE
      Visibility.GONE -> GONE
    }
  }

fun View.show() {
  vis = Visibility.VISIBLE
}

fun View.hide(visibility: Visibility.HiddenVisibility = Visibility.GONE) {
  vis = visibility as? Visibility ?: Visibility.GONE
}


val Activity.rootView: View
  get() = findViewById(android.R.id.content)