package com.mishkaowner.mvvmsample

import android.databinding.ObservableField
import com.mishkaowner.mvvmsample.base.ViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor() : ViewModel {
    @Inject lateinit var imageUrl : ObservableField<String>

    override fun onBind() {
    }
}