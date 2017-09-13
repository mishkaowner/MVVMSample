package com.mishkaowner.mvvmsample

import com.mishkaowner.mvvmsample.base.ComparableViewModel
import io.reactivex.subjects.PublishSubject

class ItemViewModel(var index: Int = 0,
                    var imageRes: Int = 0,
                    var imageUrl: String = "https://source.unsplash.com/user/erondu",
                    var name: String? = "")
    : ComparableViewModel {

    @Transient var listener: PublishSubject<ItemViewModel>? = null

    init {
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

    fun onClicked() {
       listener?.onNext(this)
    }

    fun showDetail() {
        listener?.onNext(this)
    }
}