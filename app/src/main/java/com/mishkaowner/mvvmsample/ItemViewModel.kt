package com.mishkaowner.mvvmsample

import com.mishkaowner.mvvmsample.base.ComparableViewModel
import javax.inject.Inject


class ItemViewModel(var index: Int = 0,
                    var imageRes: Int = 0,
                    var imageUrl: String = "https://source.unsplash.com/user/erondu",
                    var name: String? = "")
    : ComparableViewModel {

    @Transient @Inject lateinit var listener: ItemViewModelListener

    init{
    }

    override fun onBind() {
        MainActivity.component.inject(this)
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
        listener.remove(this)
    }

    fun showDetail(){
        listener.showDetailClicked(index)
    }
}