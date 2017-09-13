package com.mishkaowner.mvvmsample.di

import com.mishkaowner.mvvmsample.ItemViewModel
import com.mishkaowner.mvvmsample.MainActivity
import com.mishkaowner.mvvmsample.MainViewModel
import com.mishkaowner.mvvmsample.Navigator
import com.mishkaowner.mvvmsample.base.ActivityScope
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject

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
    fun providesViewModel(activity : MainActivity) : MainViewModel = MainViewModel()

    @Provides
    @ActivityScope
    fun providesItemListener() : PublishSubject<ItemViewModel> = PublishSubject.create()
}