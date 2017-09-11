package com.mishkaowner.mvvmsample

import android.databinding.ObservableField
import io.reactivex.Observable

//TODO I DON'T LIKE THE IDEA of haiving Observable in ViewModel at all....
class MainViewModel(val name: ObservableField<String> = ObservableField(""),
                         var result: ObservableField<String> = ObservableField(""),
                         val edit: ObservableField<String> = ObservableField(""))
    : ViewModel {
    @Transient var items: Observable<List<ItemViewModel>>? = null

    override fun onBind() {
        println("onBind ${name.get()}")
        result = edit.toObservable().map { "You typed $it" }.toField()
        items = name.toObservable().doOnNext{println("Value name is $it")}.map { it.length }.map { length ->
            val l: MutableList<ItemViewModel> = ArrayList()
            (0..(length % 10)).forEach {
                val item = ItemViewModel()
                item.index = it
                item.name = "New Item ${item.index}"
                if (length > 9 && it == length % 10) {
                    item.name = "Expired Item dear"
                }
                l.add(item)
            }
            l
        }
    }

    fun changeName() {
        name.set(name.get() + "1")
    }
}