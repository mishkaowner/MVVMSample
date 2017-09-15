package com.mishkaowner.mvvmsample

import com.mishkaowner.mvvmsample.base.BaseActivity
import com.mishkaowner.mvvmsample.di.DetailComponent
import com.mishkaowner.mvvmsample.di.DetailModule

class DetailActivity : BaseActivity<DetailComponent, DetailViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun newComponent(): DetailComponent? {
        return MyApp.mainAppComponent.plus(DetailModule(this))
    }

    override fun inject(component: DetailComponent?) {
        component?.inject(this)
        component?.inject(viewModel)
    }
}
