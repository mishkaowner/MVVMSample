package com.mishkaowner.mvvmsample

interface ComparableViewModel : ViewModel {
    fun compareItem(target: ComparableViewModel): Boolean

    fun compareContents(target: ComparableViewModel): Boolean
}