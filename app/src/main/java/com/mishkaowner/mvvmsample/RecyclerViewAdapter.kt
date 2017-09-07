package com.mishkaowner.mvvmsample

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

class RecyclerViewAdapter<T : ComparableViewModel>(viewModels: Observable<ArrayList<T>>,
                                                   val viewProvider: ViewProvider,
                                                   val viewModelBinder: ViewModelBinder?) : RecyclerView.Adapter<DataBindingViewHolder>() {

    private var currentItems: ArrayList<ComparableViewModel> = ArrayList()
    private val subscriptions = HashMap<RecyclerView.AdapterDataObserver, Disposable>()
    private var source: Observable<Boolean>? = null

    init {
        println("recreated")
        source = viewModels
                .applyObservableScheduler()
                .map({
                    val diffResult = DiffUtil.calculateDiff(DiffUtilComparable(currentItems, it))
                    currentItems.clear()
                    currentItems.addAll(it)
                    diffResult.dispatchUpdatesTo(RecyclerViewAdapter@this)
                    /*println("Me called please? ${it.size} ${it.hashCode()}")
                    val list = ArrayList<ComparableViewModel>()
                    for(item in it) { list.add(item) }
                    currentItems = list
                    *//*diffResult.dispatchUpdatesTo(RecyclerViewAdapter@this)
                    diffResult*//*
                    notifyDataSetChanged()*/
                    true
                })
                .share()
                .doOnError({println("Error $it")})
    }

    fun <T> Observable<T>.applyObservableScheduler() = subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())!!

    override fun getItemViewType(position: Int): Int {
        return viewProvider.getView(currentItems[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        viewModelBinder?.bind(holder.viewBinding, currentItems[position])
        holder.viewBinding.executePendingBindings()
    }

    override fun onViewRecycled(holder: DataBindingViewHolder?) {
        viewModelBinder?.bind(holder!!.viewBinding, null)
        holder?.viewBinding?.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return currentItems.size
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        subscriptions.put(observer, source!!.subscribe())
        super.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
        val subscription = subscriptions.remove(observer)
        if (subscription != null && !subscription.isDisposed) {
            subscription.dispose()
        }
    }
}