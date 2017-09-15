package com.mishkaowner.mvvmsample

import com.mishkaowner.mvvmsample.base.ComparableViewModel
import com.mishkaowner.mvvmsample.base.ItemViewModel
import io.reactivex.subjects.PublishSubject

class MainItemViewModel(var index: Int = 0,
                        var imageRes: Int = 0,
                        var imageUrl: String = "https://source.unsplash.com/user/erondu",
                        var name: String? = "")
    : ItemViewModel<Pair<MainItemViewModel, Int>>() {

    @Transient var listener: PublishSubject<Pair<MainItemViewModel, Int>>? = null

    override fun onBind(listener: PublishSubject<Pair<MainItemViewModel, Int>>) {
        this.listener = listener
    }

    fun hasImage(): Boolean {
        return imageRes != 0
    }

    override fun compareItem(target: ComparableViewModel): Boolean {
        val targetItem = target as MainItemViewModel
        return index == targetItem.index
    }

    override fun compareContents(target: ComparableViewModel): Boolean {
        val targetItem = target as MainItemViewModel
        return name!! == targetItem.name
    }

    fun onClicked() {
       listener?.onNext(Pair(this, 1))
    }

    fun showDetail() {
        listener?.onNext(Pair(this, 0))
    }
}