package com.mishkaowner.mvvmsample.di

import android.databinding.ObservableField
import com.mishkaowner.mvvmsample.DetailActivity
import com.mishkaowner.mvvmsample.DetailViewModel
import com.mishkaowner.mvvmsample.base.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class DetailModule(private val activity : DetailActivity) {
    @ActivityScope @Provides fun providesActivity() : DetailActivity = activity

    @ActivityScope @Provides fun providesUrlstring(activity : DetailActivity): ObservableField<String> = ObservableField(activity.intent.extras.getString("url"))

    @ActivityScope @Provides fun providesDetailViewModel() : DetailViewModel  = DetailViewModel()
}