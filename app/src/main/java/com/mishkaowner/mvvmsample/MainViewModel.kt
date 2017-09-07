package com.mishkaowner.mvvmsample

import android.databinding.ObservableField
import io.reactivex.Observable

class MainViewModel(val name: ObservableField<String> = ObservableField(""),
                    var result: ObservableField<String> = ObservableField(""),
                    val edit: ObservableField<String> = ObservableField(""))
    : ViewModel {
    @Transient var items: Observable<ArrayList<ItemViewModel>>? = null
    var list : ArrayList<ItemViewModel> = ArrayList()

    init {
        result = edit.toObservable().map { "You typed $it" }.toField()
        items = name.toObservable().map { it.length }.map{
            println("Val ${it%10}")
            if(it > 5) {
                val item = ItemViewModel()
                item.name = "New Item $it"
                item.index = it
                list.add(0, item)
            } else {
                val item = ItemViewModel()
                item.name = "New Item $it"
                item.index = it
                list.add(item)
            }
            list
        }
    }

    fun changeName() {
        name.set(name.get() + "1")
    }
}