package com.kevinmost.kotlin_toolbelt.extension

import rx.Observable
import rx.Scheduler
import rx.schedulers.Schedulers
import java.util.concurrent.Future

@Suppress("UNCHECKED_CAST")
@JvmOverloads
fun <T : Observable<*>> T.applySchedulers(doWorkOnThread: Scheduler = Schedulers.io(),
    observeOnThread: Scheduler): T {
  return this
      .subscribeOn(doWorkOnThread)
      .observeOn(observeOnThread) as T
}

/**
 * @return An [Observable] that mirrors the receiver Observable, but that outputs the latest
 * [numToCache] values to anyone that subscribes later
 */
@JvmOverloads
fun <T> Observable<T>.cacheLatest(numToCache: Int = 1): Observable<T> {
  return this.replay(numToCache).autoConnect()
}

fun <T> Future<T>.toObservable(): Observable<T> = Observable.from(this)

fun <T> Future<T>.onReturn(doOnReturn: (T) -> Unit) {
  toObservable().subscribe { doOnReturn(it) }
}

