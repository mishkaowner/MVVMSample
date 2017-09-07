package com.mishkaowner.mvvmsample

import io.reactivex.functions.Action


class ItemViewModel : ComparableViewModel {
    var index: Int = 0

    var imageRes: Int = 0

    var name: String? = null

    var onClicked: Action? = null

    var onDetailsClicked: Action? = null

    fun hasImage(): Boolean {
        return imageRes != 0
    }

    init {
        this.name = ""
        this.onClicked = Action { println("Clicked onClicked") }
        this.onDetailsClicked = Action { println("Clicked onDetailsClicked") }
    }

    override fun compareItem(target: ComparableViewModel): Boolean {
        val targetItem = target as ItemViewModel
        return index == targetItem.index
    }

    override fun compareContents(target: ComparableViewModel): Boolean {
        val targetItem = target as ItemViewModel
        return name!! == targetItem.name
    }
}