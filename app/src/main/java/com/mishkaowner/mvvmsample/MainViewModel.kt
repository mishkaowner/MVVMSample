package com.mishkaowner.mvvmsample

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import com.mishkaowner.mvvmsample.base.ViewModel
import com.mishkaowner.mvvmsample.base.toField
import com.mishkaowner.mvvmsample.base.toObservable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

//TODO I DON'T LIKE THE IDEA of haiving Observable in ViewModel at all....
class MainViewModel @Inject constructor() : ViewModel {
    val name: ObservableField<String> = ObservableField("")
    var result: ObservableField<String> = ObservableField("")
    val edit: ObservableField<String> = ObservableField("")
    val itemSource: ObservableArrayList<MainItemViewModel> = ObservableArrayList()
    @Transient @Inject lateinit var navi : Navigator
    @Transient @Inject lateinit var itemListener : PublishSubject<Pair<MainItemViewModel, Int>>
    @Transient var items: Observable<List<MainItemViewModel>>? = null

    override fun onBind() {
        result = edit.toObservable().map { "You typed $it" }.toField()
        items = itemSource.toObservable()
        itemListener.subscribe({
            when(it.second){
                0 -> navi.showItemDetail(it.first, itemSource.indexOf(it.first))
                else -> itemSource.remove(it.first)
            }
        })
    }

    fun changeName() {
        addItem()
    }

    private fun addItem(){
        val item = MainItemViewModel()
        item.index = itemSource.size
        item.name = "New Item ${item.index}"
        itemSource.add(item)
    }
}