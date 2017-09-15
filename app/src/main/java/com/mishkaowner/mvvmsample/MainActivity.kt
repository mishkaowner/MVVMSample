package com.mishkaowner.mvvmsample

import android.app.Activity
import android.support.v4.util.Pair
import android.view.View
import android.view.ViewGroup
import com.mishkaowner.mvvmsample.base.BaseActivity
import com.mishkaowner.mvvmsample.base.Transitionable
import com.mishkaowner.mvvmsample.di.MainComponent
import com.mishkaowner.mvvmsample.di.MainModule
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainComponent, MainViewModel>(), Transitionable {
    override fun getTransitionalViews(pos: Int): Array<Pair<View, String>> {
        val list = arrayListOf<Pair<View, String>>()
        val selctedView = recyclerView.getChildAt(pos) as ViewGroup
        (0 until selctedView.childCount).forEach {
            val v = selctedView.getChildAt(it)
            if(v != null && v.transitionName != null) {
                list.add(Pair(v, v.transitionName))
            }
        }
        return list.toTypedArray()
    }

    override fun getTransitionalContext(): Activity {
        return this
    }

    override fun newComponent(): MainComponent? {
        return MyApp.mainAppComponent.plus(MainModule(this))
    }

    override fun inject(component: MainComponent?) {
        component?.inject(this)
        component?.inject(viewModel)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}