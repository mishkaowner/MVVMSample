package com.mishkaowner.mvvmsample

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class AACVM : ViewModel() {
    var varA : MutableLiveData<Int> = MutableLiveData()
}