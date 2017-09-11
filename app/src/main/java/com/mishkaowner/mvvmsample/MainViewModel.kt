package com.mishkaowner.mvvmsample

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.support.v7.view.menu.MenuView
import com.mishkaowner.mvvmsample.base.ViewModel
import com.mishkaowner.mvvmsample.base.toField
import com.mishkaowner.mvvmsample.base.toObservable
import io.reactivex.Observable

//TODO I DON'T LIKE THE IDEA of haiving Observable in ViewModel at all....
class MainViewModel(val name: ObservableField<String> = ObservableField(""),
                    var result: ObservableField<String> = ObservableField(""),
                    val edit: ObservableField<String> = ObservableField(""))
    : ViewModel {
    @Transient
    var items: Observable<List<ItemViewModel>>? = null

    override fun onBind() {
        println("onBind ${name.get()}")
        result = edit.toObservable().map { "You typed $it" }.toField()
        items = name.toObservable().doOnNext { println("Value name is $it") }.map { it.length }.map { length ->
            val l: MutableList<ItemViewModel> = ArrayList()
            (0..length).forEach {
                val item = ItemViewModel()
                item.index = it
                item.name = "New Item ${item.index}"
                if (length % 10 == 3) {
                    item.name = "Expired Item dear"
                }
                l.add(item)
            }
            l
        }
        //result = scrollPos.toObservable().doOnNext{println("Position $it")}.map{ it.toString() }.toField()
    }

    fun changeName() {
        name.set(name.get() + "1")
    }
}