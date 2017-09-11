package com.mishkaowner.mvvmsample.base

import android.databinding.ViewDataBinding

interface ViewModelBinder {
    fun bind(viewDataBinding: ViewDataBinding, viewModel: ViewModel?)
}