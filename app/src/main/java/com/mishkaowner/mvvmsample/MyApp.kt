package com.mishkaowner.mvvmsample

import android.app.Application
import android.content.Context
import com.mishkaowner.mvvmsample.di.AppComponent
import com.mishkaowner.mvvmsample.di.AppModule
import com.mishkaowner.mvvmsample.di.DaggerAppComponent
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
        mainAppComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        @JvmStatic lateinit var mainAppComponent: AppComponent
    }
}