package com.mishkaowner.mvvmsample.di

import com.mishkaowner.mvvmsample.MainItemViewModel
import com.mishkaowner.mvvmsample.MainActivity
import com.mishkaowner.mvvmsample.MainViewModel
import com.mishkaowner.mvvmsample.Navigator
import com.mishkaowner.mvvmsample.base.ActivityScope
import com.mishkaowner.mvvmsample.base.Transitionable
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject

@Module
class MainModule(private val activity : MainActivity) {
    @Provides
    @ActivityScope
    fun providesActivity(): MainActivity = activity

    @Provides
    @ActivityScope
    fun providesTrans(activity : MainActivity) : Transitionable = activity

    @Provides
    @ActivityScope
    fun providesNavi(transitionable: Transitionable) : Navigator = Navigator(transitionable)

    @Provides
    @ActivityScope
    fun providesViewModel() : MainViewModel = MainViewModel()

    @Provides
    @ActivityScope
    fun providesItemListener() : PublishSubject<Pair<MainItemViewModel, Int>> = PublishSubject.create()
}