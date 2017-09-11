package com.mishkaowner.mvvmsample.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RecyclerViewAdapter<T : ComparableViewModel>(viewModels: Observable<List<T>>,
                                                   val viewProvider: ViewProvider,
                                                   val viewModelBinder: ViewModelBinder?) : RecyclerView.Adapter<DataBindingViewHolder>() {

    private var currentItems: MutableList<ComparableViewModel> = ArrayList()
    private val subscriptions = HashMap<RecyclerView.AdapterDataObserver, Disposable>()
    private var source: Observable<List<T>>? = null

    init {
        source = viewModels
                .applyObservableScheduler()
                .doOnNext({
                    val diffResult = DiffUtil.calculateDiff(DiffUtilComparable(currentItems, it))
                    currentItems.clear()
                    currentItems.addAll(it)
                    diffResult.dispatchUpdatesTo(RecyclerViewAdapter@ this)
                })
                .share()
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
        subscriptions.put(observer, source!!.subscribe({}, {println("Errorr $it")}))
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