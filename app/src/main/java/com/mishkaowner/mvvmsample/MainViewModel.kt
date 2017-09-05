package com.mishkaowner.mvvmsample

import android.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.functions.Action

class MainViewModel(val name : ObservableField<String> = ObservableField("hello"),
                    var result : ObservableField<String> = ObservableField(""),
                    val edit : ObservableField<String> = ObservableField("")) : ViewModel{

    var itemVms: Observable<List<ViewModel>>
    var btClicked : Action? = null

    init {
        result = edit.toObservable().map{"You typed $it"}.toField()
        itemVms = Observable.range(0, 100).map{
            val item = ItemViewModel()
            item.name = "$it is it"
            item as ViewModel
        }.toList().toObservable()
        btClicked = Action {
            name.set("New Hello")
        }
    }

    fun changeName(){
        name.set("New")
    }
}