package com.mishkaowner.mvvmsample

interface ItemViewModelListener {
    fun remove(item: MainItemViewModel)

    fun showDetailClicked(index: Int)
}