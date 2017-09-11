package com.mishkaowner.mvvmsample

import com.mishkaowner.mvvmsample.base.ComparableViewModel
import io.reactivex.functions.Action


class ItemViewModel(var index: Int = 0,
                    var imageRes: Int = 0,
                    var name: String? = "")
    : ComparableViewModel {
    @Transient var onClicked: Action? = null
    @Transient var onDetailsClicked: Action? = null

    init{
        this.onClicked = Action { println("Clicked onClicked") }
        this.onDetailsClicked = Action { println("Clicked onDetailsClicked") }
    }

    override fun onBind() {
    }

    fun hasImage(): Boolean {
        return imageRes != 0
    }

    override fun compareItem(target: ComparableViewModel): Boolean {
        val targetItem = target as ItemViewModel
        return index == targetItem.index
    }

    override fun compareContents(target: ComparableViewModel): Boolean {
        val targetItem = target as ItemViewModel
        return name!! == targetItem.name
    }

    fun onClicked(){
        println("Clicked item")
    }
}