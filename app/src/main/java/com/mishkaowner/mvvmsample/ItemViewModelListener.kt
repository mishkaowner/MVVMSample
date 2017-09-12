package com.mishkaowner.mvvmsample

interface ItemViewModelListener {
    fun remove(item:ItemViewModel)
    fun showDetailClicked(index: Int)
}