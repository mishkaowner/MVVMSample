package com.mishkaowner.mvvmsample.di

import com.mishkaowner.mvvmsample.DetailActivity
import com.mishkaowner.mvvmsample.DetailViewModel
import com.mishkaowner.mvvmsample.base.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(DetailModule::class))
interface DetailComponent {
    fun inject(activity : DetailActivity)
    fun inject(viewModel : DetailViewModel)
}