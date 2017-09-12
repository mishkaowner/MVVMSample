package com.mishkaowner.mvvmsample

import com.mishkaowner.mvvmsample.base.BaseActivity
import com.mishkaowner.mvvmsample.di.MainComponent
import com.mishkaowner.mvvmsample.di.MainModule

class MainActivity : BaseActivity<MainComponent, MainViewModel>() {
    companion object {
        @JvmStatic lateinit var component: MainComponent
    }

    override fun newComponent(): MainComponent? {
        component = MyApp.mainAppComponent.plus(MainModule(this))
        return component
    }

    override fun inject(component: MainComponent?) {
        component?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}