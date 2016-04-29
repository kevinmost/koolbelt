package com.kevinmost.kotlin_extension.android

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            getChildOfType(type)?.let { return it }
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

fun <T: View> Fragment.inflate(
        @LayoutRes layout: Int,
        into: ViewGroup? = null,
        attachToParent: Boolean = false
): T {
    return context.inflate(layout, into, attachToParent)
}
