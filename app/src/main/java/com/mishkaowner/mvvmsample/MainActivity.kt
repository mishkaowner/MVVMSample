package com.mishkaowner.mvvmsample

import android.app.Activity

class MainActivity : BaseActivity<MainViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}
