package com.mishkaowner.mvvmsample.base

interface ComparableViewModel : ViewModel {
    fun compareItem(target: ComparableViewModel): Boolean

    fun compareContents(target: ComparableViewModel): Boolean
}