package com.mishkaowner.mvvmsample.di

import com.mishkaowner.mvvmsample.MainViewModel
import com.mishkaowner.mvvmsample.MyApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun plus(module : MainModule) : MainComponent
    fun inject(app : MyApp)
}