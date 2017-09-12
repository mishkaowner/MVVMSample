package com.mishkaowner.mvvmsample.di

import com.mishkaowner.mvvmsample.ItemViewModel
import com.mishkaowner.mvvmsample.MainViewModel
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(vm: MainViewModel)
    fun inject(vm: ItemViewModel)
}