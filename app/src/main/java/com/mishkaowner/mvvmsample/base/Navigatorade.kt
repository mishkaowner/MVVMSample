package com.mishkaowner.mvvmsample.base

import android.app.Activity
import android.view.View

interface Navigatorade {
    fun get() : Activity

    fun getView(pos : Int) : View
}