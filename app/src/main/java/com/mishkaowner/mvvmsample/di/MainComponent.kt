package com.mishkaowner.mvvmsample.di

import com.mishkaowner.mvvmsample.ItemViewModel
import com.mishkaowner.mvvmsample.MainActivity
import com.mishkaowner.mvvmsample.MainViewModel
import com.mishkaowner.mvvmsample.base.ActivityScope
import dagger.Subcomponent
@ActivityScope
@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(activity: MainActivity)
    fun inject(vm: ItemViewModel)
}