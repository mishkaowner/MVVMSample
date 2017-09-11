package com.mishkaowner.mvvmsample.base

import android.databinding.Observable.OnPropertyChangedCallback
import android.databinding.ObservableField
import io.reactivex.Observable


fun <T> ObservableField<T>.toObservable(): Observable<T> = Observable.create { e ->
    val initialValue = this@toObservable.get()
    if (initialValue != null) {
        e.onNext(initialValue)
    }
    val callback = object : OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: android.databinding.Observable, i: Int) {
            e.onNext(this@toObservable.get())
        }
    }
    this@toObservable.addOnPropertyChangedCallback(callback)
    e.setCancellable { this@toObservable.removeOnPropertyChangedCallback(callback) }
}

fun <T> Observable<T>.toField(): ReadOnlyField<T> = ReadOnlyField.create(this@toField)

