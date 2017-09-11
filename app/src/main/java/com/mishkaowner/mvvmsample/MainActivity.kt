package com.mishkaowner.mvvmsample

import com.mishkaowner.mvvmsample.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}