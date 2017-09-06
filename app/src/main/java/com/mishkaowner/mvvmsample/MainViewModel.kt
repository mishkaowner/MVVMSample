package com.mishkaowner.mvvmsample

import android.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.functions.Action

class MainViewModel(val name : ObservableField<String> = ObservableField("Old"),
                    var result : ObservableField<String> = ObservableField(""),
                    val edit : ObservableField<String> = ObservableField("")) : ViewModel{
    @Transient var itemVms: Observable<List<ViewModel>>
    val items : ObservableField<List<ItemViewModel>> = ObservableField()
    var list : MutableList<ItemViewModel>

    init {
        result = edit.toObservable().map{"You typed $it"}.toField()
        itemVms = Observable.range(0, 100).map{
            val item = ItemViewModel()
            item.name = "$it is it"
            item as ViewModel
        }.toList().toObservable()
        list = mutableListOf()
        items.set(list)
        val callback = object : android.databinding.Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: android.databinding.Observable, i: Int) {
                println("PRoperty chanbged")
            }
        }
        items.addOnPropertyChangedCallback(callback)
    }

    fun changeName(){
        println("size of list ${items.get().size} ")

        name.set("New")

        val item = ItemViewModel()
        item.name = "it"
        list.add(item)
    }
}