package com.mishkaowner.mvvmsample.di

import com.mishkaowner.mvvmsample.ItemViewModelListener
import com.mishkaowner.mvvmsample.MainActivity
import com.mishkaowner.mvvmsample.MainViewModel
import com.mishkaowner.mvvmsample.Navigator
import com.mishkaowner.mvvmsample.base.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class MainModule(val activity : MainActivity) {
    @Provides
    @ActivityScope
    fun providesActivity(): MainActivity = activity

    @Provides
    @ActivityScope
    fun providesNavi(activity: MainActivity) = Navigator(activity)

    @Provides
    @ActivityScope
    fun providesMainVm () : MainViewModel = MainViewModel()

    @Provides
    @ActivityScope
    fun providesItemListener(vm : MainViewModel) : ItemViewModelListener = vm
}