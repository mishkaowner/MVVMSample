package com.mishkaowner.mvvmsample

import android.databinding.ObservableField

class MainViewModel(val name : ObservableField<String> = ObservableField("hello"),
                    var result : ObservableField<String> = ObservableField(""),
                    val edit : ObservableField<String> = ObservableField("")) {
    init {
        result = name.toObservable().map{"You typed $it"}.toField()
    }

    fun changeName(){
        name.set("New hello")
    }
}