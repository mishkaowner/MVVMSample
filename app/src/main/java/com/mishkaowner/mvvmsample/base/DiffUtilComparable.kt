package com.mishkaowner.mvvmsample.base

import android.support.v7.util.DiffUtil

class DiffUtilComparable<out T : ComparableViewModel>(val oldItems: List<T>, val newItems: List<T>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].compareItem(newItems[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].compareContents(newItems[newItemPosition])
    }
}