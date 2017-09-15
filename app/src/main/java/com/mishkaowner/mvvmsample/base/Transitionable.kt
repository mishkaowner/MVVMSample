package com.mishkaowner.mvvmsample.base

import android.app.Activity
import android.view.View

interface Transitionable {
    fun getTransitionalContext() : Activity?

    fun getTransitionalViews(pos : Int) : Array<android.support.v4.util.Pair<View, String>>
}