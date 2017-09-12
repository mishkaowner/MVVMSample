package com.mishkaowner.mvvmsample

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import com.mishkaowner.mvvmsample.base.ViewModel
import com.mishkaowner.mvvmsample.base.toField
import com.mishkaowner.mvvmsample.base.toObservable
import com.mishkaowner.mvvmsample.di.MainComponent
import com.mishkaowner.mvvmsample.di.MainModule
import io.reactivex.Observable
import javax.inject.Inject

//TODO I DON'T LIKE THE IDEA of haiving Observable in ViewModel at all....
class MainViewModel(val name: ObservableField<String> = ObservableField(""),
                    var result: ObservableField<String> = ObservableField(""),
                    val edit: ObservableField<String> = ObservableField(""),
                    val itemSource: ObservableArrayList<ItemViewModel> = ObservableArrayList())
    : ViewModel, ItemViewModelListener {

    companion object {
        @JvmStatic lateinit var component: MainComponent
    }

    override fun remove(item: ItemViewModel) {
        itemSource.remove(item)
    }

    @Transient
    var items: Observable<List<ItemViewModel>>? = null

    override fun onBind() {
        component = MyApp.mainAppComponent.plus(MainModule(this))
        component.inject(this)

        result = edit.toObservable().map { "You typed $it" }.toField()
        items = itemSource.toObservable()
    }

    fun changeName() {
        addItem()
    }

    private fun addItem(){
        val item = ItemViewModel()
        item.index = itemSource.size
        item.name = "New Item ${item.index}"
        item.listener = this
        itemSource.add(item)
    }
}