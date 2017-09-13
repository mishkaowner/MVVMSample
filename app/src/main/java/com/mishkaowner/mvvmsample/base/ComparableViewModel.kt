package com.mishkaowner.mvvmsample.base

import io.reactivex.subjects.PublishSubject

interface ComparableViewModel : ViewModel {
    fun compareItem(target: ComparableViewModel): Boolean

    fun compareContents(target: ComparableViewModel): Boolean
}