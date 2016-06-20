package com.kevinmost.koolbelt.extension.android

import timber.log.Timber
import timber.log.Timber.Tree

val __MOCK_THROWABLE = RuntimeException("MOCK")

fun timber(): Tree = Timber.asTree()

fun __loggingWillDoAnything() = Timber.treeCount() > 0

inline fun Tree.v(throwable: Throwable = __MOCK_THROWABLE, messageSupplier: () -> String) {
  if (__loggingWillDoAnything()) {
    if (throwable == __MOCK_THROWABLE) {
      Timber.v(messageSupplier())
    } else {
      Timber.v(throwable, messageSupplier())
    }
  }
}

inline fun Tree.d(throwable: Throwable = __MOCK_THROWABLE, messageSupplier: () -> String) {
  if (__loggingWillDoAnything()) {
    if (throwable == __MOCK_THROWABLE) {
      Timber.d(messageSupplier())
    } else {
      Timber.d(throwable, messageSupplier())
    }
  }
}

inline fun Tree.i(throwable: Throwable = __MOCK_THROWABLE, messageSupplier: () -> String) {
  if (__loggingWillDoAnything()) {
    if (throwable == __MOCK_THROWABLE) {
      Timber.i(messageSupplier())
    } else {
      Timber.i(throwable, messageSupplier())
    }
  }
}

inline fun Tree.w(throwable: Throwable = __MOCK_THROWABLE, messageSupplier: () -> String) {
  if (__loggingWillDoAnything()) {
    if (throwable == __MOCK_THROWABLE) {
      Timber.w(messageSupplier())
    } else {
      Timber.w(throwable, messageSupplier())
    }
  }
}

inline fun Tree.e(throwable: Throwable = __MOCK_THROWABLE, messageSupplier: () -> String) {
  if (__loggingWillDoAnything()) {
    if (throwable == __MOCK_THROWABLE) {
      Timber.e(messageSupplier())
    } else {
      Timber.e(throwable, messageSupplier())
    }
  }
}

inline fun Tree.wtf(throwable: Throwable = __MOCK_THROWABLE, messageSupplier: () -> String) {
  if (__loggingWillDoAnything()) {
    if (throwable == __MOCK_THROWABLE) {
      Timber.wtf(messageSupplier())
    } else {
      Timber.wtf(throwable, messageSupplier())
    }
  }
}

