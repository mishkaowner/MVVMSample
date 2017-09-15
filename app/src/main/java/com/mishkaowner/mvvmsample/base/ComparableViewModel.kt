package com.mishkaowner.mvvmsample.base

interface ComparableViewModel {
    fun compareItem(target: ComparableViewModel): Boolean

    fun compareContents(target: ComparableViewModel): Boolean
}