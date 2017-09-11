package com.mishkaowner.mvvmsample.base

import android.support.annotation.LayoutRes

interface ViewProvider {
    @LayoutRes fun getView(vm: ViewModel): Int
}