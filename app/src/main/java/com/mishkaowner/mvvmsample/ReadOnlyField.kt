package com.mishkaowner.mvvmsample

import android.databinding.ObservableField
import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class ReadOnlyField<T>() : ObservableField<T>() {
    lateinit var source : Observable<T>
    val subscriptions = HashMap<android.databinding.Observable.OnPropertyChangedCallback, Disposable>()

    companion object {
        fun <U> create(source: Observable<U>): ReadOnlyField<U> {
            return ReadOnlyField(source)
        }
    }

    constructor(source: Observable<T>) : this() {
        this.source = source.
                doOnNext({ t -> super.set(t) })
                .doOnError({ throwable -> Log.e("ReadOnlyField", "onError in source observable", throwable) })
                .onErrorResumeNext(Observable.empty<T>()).share()
    }

    @Deprecated("")
    override fun set(value: T) {
    }

    @Synchronized override fun addOnPropertyChangedCallback(callback: android.databinding.Observable.OnPropertyChangedCallback) {
        super.addOnPropertyChangedCallback(callback)
        subscriptions.put(callback, source.subscribe())
    }

    @Synchronized override fun removeOnPropertyChangedCallback(callback: android.databinding.Observable.OnPropertyChangedCallback) {
        super.removeOnPropertyChangedCallback(callback)
        val subscription = subscriptions.remove(callback)
        if (subscription != null && !subscription.isDisposed) {
            subscription.dispose()
        }
    }
}