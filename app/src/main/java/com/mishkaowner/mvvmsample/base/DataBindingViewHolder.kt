package com.mishkaowner.mvvmsample.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import io.reactivex.subjects.PublishSubject

class DataBindingViewHolder(val viewBinding: ViewDataBinding)  : RecyclerView.ViewHolder(viewBinding.root)
