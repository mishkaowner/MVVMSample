package com.mishkaowner.mvvmsample

import android.support.annotation.LayoutRes

interface ViewProvider {
    @LayoutRes fun getView(vm: ViewModel): Int
}