package com.mishkaowner.mvvmsample

class MainActivity : BaseActivity<MainViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}