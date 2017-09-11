package com.mishkaowner.mvvmsample.base

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.databinding.ObservableField
import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mishkaowner.mvvmsample.*
import io.reactivex.Observable
import io.reactivex.functions.Action

object BindingAdapters {
    val defaultBinder: ViewModelBinder = object : ViewModelBinder {
        override fun bind(viewDataBinding: ViewDataBinding, viewModel: ViewModel?) {
            viewDataBinding.setVariable(BR.vm, viewModel)
        }
    }

    @JvmStatic
    @BindingConversion
    fun getViewProviderForStaticLayout(layoutId: Int): ViewProvider? {
        return object : ViewProvider {
            override fun getView(vm: ViewModel): Int {
                return layoutId
            }
        }
    }

    @JvmStatic
    @BindingConversion
    fun toOnClickListener(listener: Action?): View.OnClickListener? {
        return if (listener != null) {
            View.OnClickListener {
                try {
                    listener.run()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            null
        }
    }

   /* @JvmStatic
    @BindingAdapter("items", "view_provider")
    fun bindRecyclerViewAdapter(recyclerView: RecyclerView, items: io.reactivex.Observable<List<ViewModel>>, viewProvider: ViewProvider) {
        recyclerView.adapter = RecyclerViewAdapter(items, viewProvider, defaultBinder)
    }*/

    @JvmStatic
    @BindingAdapter("items", "view_provider")
    fun <T : ComparableViewModel>bindRecyclerViewAdapter(recyclerView: RecyclerView, items: Observable<List<T>>?, viewProvider: ViewProvider?) {
        if(items != null && viewProvider != null)
            recyclerView.adapter = RecyclerViewAdapter(items, viewProvider, defaultBinder)
    }
    /*@JvmStatic
    @BindingAdapter("list", "view_provider")
    fun bindRecyclerViewAdapter2(recyclerView: RecyclerView, list: io.reactivex.Observable<List<ViewModel>>, viewProvider: ViewProvider) {
        recyclerView.adapter = RecyclerViewAdapter2(list, viewProvider, defaultBinder)
    }*/

    @JvmStatic
    @BindingAdapter("layout_vertical")
    fun bindLayoutManager(recyclerView: RecyclerView, vertical: Boolean) {
        val orientation = if (vertical) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, orientation, false)
    }

    /*@JvmStatic
    @BindingAdapter("scroll_position")
    fun bindScrollPosition(recyclerView: RecyclerView, scrollPosition: ObservableField<Int>) {
        recyclerView.scrollToPosition(scrollPosition.get())
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(scrollPosition.get() != dy) {
                    scrollPosition.set(dy)
                }
            }
        })
    }*/
}