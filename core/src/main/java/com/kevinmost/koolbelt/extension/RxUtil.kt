package com.kevinmost.koolbelt.extension

import rx.Observable
import rx.Scheduler
import rx.schedulers.Schedulers
import rx.subjects.AsyncSubject
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject
import rx.subjects.ReplaySubject
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

inline fun <T> Future<T>.onReturn(crossinline doOnReturn: (T) -> Unit) {
  toObservable().subscribe { doOnReturn(it) }
}

infix fun <T1, T2> Observable<T1>.combineWith(other: Observable<T2>): Observable<Pair<T1, T2>> {
  return Observable.combineLatest(this, other) { first, second -> first to second }
}

infix fun <T1, T2> Observable<T1>.zipWith(other: Observable<T2>): Observable<Pair<T1, T2>> {
  return this.zipWith(other) { first, second -> first to second }
}


var <T> BehaviorSubject<T>.currentValue: T
  get() = this.value
  set(value) = onNext(value)

var <T> PublishSubject<T>.currentValue: T
  get() = throw UnsupportedOperationException("PublishSubject does not cache its emitted values; there is no concept of a 'current' value to retrieve!")
  set(value) = onNext(value)

var <T> ReplaySubject<T>.currentValue: T
  get() = this.value
  set(value) = onNext(value)

var <T> AsyncSubject<T>.currentValue: T
  get() = this.value
  set(value) = onNext(value)