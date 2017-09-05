package com.mishkaowner.mvvmsample

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class RecyclerViewAdapter(viewModels: Observable<List<ViewModel>>,
                          val viewProvider: ViewProvider,
                          val viewModelBinder: ViewModelBinder?) : RecyclerView.Adapter<DataBindingViewHolder>() {

    private var latestViewModels : List<ViewModel> = ArrayList()
    private var source: Observable<List<ViewModel>>? = null
    private val subscriptions = HashMap<RecyclerView.AdapterDataObserver, Disposable>()

    init {
        source = viewModels
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext({
                    latestViewModels = it
                    notifyDataSetChanged()
                })
                .share()
    }

    override fun getItemViewType(position: Int): Int {
        return viewProvider.getView(latestViewModels[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        viewModelBinder?.bind(holder.viewBinding, latestViewModels[position])
        holder.viewBinding.executePendingBindings()
    }

    override fun onViewRecycled(holder: DataBindingViewHolder?) {
        viewModelBinder?.bind(holder!!.viewBinding, null)
        holder?.viewBinding?.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return latestViewModels.size
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