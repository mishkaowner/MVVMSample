package com.mishkaowner.mvvmsample

import android.databinding.ViewDataBinding

interface ViewModelBinder {
    fun bind(viewDataBinding: ViewDataBinding, viewModel: ViewModel?)
}