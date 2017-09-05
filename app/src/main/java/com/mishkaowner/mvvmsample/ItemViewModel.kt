package com.mishkaowner.mvvmsample

import io.reactivex.functions.Action


class ItemViewModel : ViewModel {
    var imageRes: Int = 0

    var name: String? = null

    var onClicked: Action? = null

    var onDetailsClicked: Action? = null

    fun hasImage(): Boolean {
        return imageRes != 0
    }

    init {
        this.name = "asfdasdf"
        this.onClicked = Action { println("Clicked onClicked") }
        this.onDetailsClicked = Action { println("Clicked onDetailsClicked") }
    }
}