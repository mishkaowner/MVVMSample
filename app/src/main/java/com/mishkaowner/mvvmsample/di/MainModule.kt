package com.mishkaowner.mvvmsample.di

import com.mishkaowner.mvvmsample.ItemViewModelListener
import com.mishkaowner.mvvmsample.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainModule(val vm : MainViewModel) {
    @Provides
    fun providesMainVm () : MainViewModel = vm

    @Provides
    fun providesItemListener(vm : MainViewModel) : ItemViewModelListener = vm
}