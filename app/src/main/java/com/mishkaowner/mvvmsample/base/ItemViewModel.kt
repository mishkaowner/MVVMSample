package com.mishkaowner.mvvmsample.base

import io.reactivex.subjects.PublishSubject

abstract class ItemViewModel<T> : ViewModel, ComparableViewModel {
    override fun onBind() {}

    abstract fun onBind(listener : PublishSubject<T>)
}