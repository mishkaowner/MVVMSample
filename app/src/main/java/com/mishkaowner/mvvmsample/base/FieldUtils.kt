package com.mishkaowner.mvvmsample.base

import android.databinding.Observable.OnPropertyChangedCallback
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableList
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

fun <T> ObservableArrayList<T>.toObservable():Observable<List<T>> = Observable.create { e ->
    val initialValue = this@toObservable.toList()
    if (initialValue != null) {
        e.onNext(initialValue)
    }

    val callback = object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
        override fun onItemRangeRemoved(p0: ObservableList<T>?, p1: Int, p2: Int) {
            if(p0 != null) e.onNext(p0.toList())
        }

        override fun onItemRangeMoved(p0: ObservableList<T>?, p1: Int, p2: Int, p3: Int) {
            if(p0 != null) e.onNext(p0.toList())
        }

        override fun onItemRangeInserted(p0: ObservableList<T>?, p1: Int, p2: Int) {
            if(p0 != null) e.onNext(p0.toList())
        }

        override fun onItemRangeChanged(p0: ObservableList<T>?, p1: Int, p2: Int) {
            if(p0 != null) e.onNext(p0.toList())
        }

        override fun onChanged(p0: ObservableList<T>?) {
            if(p0 != null) e.onNext(p0.toList())
        }
    }
    this@toObservable.addOnListChangedCallback(callback)
    e.setCancellable { this@toObservable.removeOnListChangedCallback(callback) }
}

fun <T> Observable<T>.toField(): ReadOnlyField<T> = ReadOnlyField.create(this@toField)