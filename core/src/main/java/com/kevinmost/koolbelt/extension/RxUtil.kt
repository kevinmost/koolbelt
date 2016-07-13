package com.kevinmost.koolbelt.extension

import rx.Observable
import rx.Scheduler
import rx.schedulers.Schedulers
import rx.subjects.AsyncSubject
import rx.subjects.BehaviorSubject
import rx.subjects.ReplaySubject
import rx.subjects.Subject
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

var <T> Subject<T, T>.currentValue: T
  get() = when(this) {
    is BehaviorSubject<T> -> this.value
    is AsyncSubject<T> -> this.value
    is ReplaySubject<T> -> this.value
    else -> last().toBlocking().first() // TODO: This seems right, but... does it work?
  }
  set(value) = onNext(value)
