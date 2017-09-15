package com.mishkaowner.mvvmsample

import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import com.mishkaowner.mvvmsample.base.Transitionable

class Navigator (val trans : Transitionable) {
    fun showItemDetail(itemViewModel: MainItemViewModel?, index : Int) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(trans.getTransitionalContext(), *trans.getTransitionalViews(index))
        trans.getTransitionalContext()?.startActivity(Intent(trans.getTransitionalContext(), DetailActivity::class.java).putExtra("url", itemViewModel?.imageUrl?:""), options.toBundle())
    }
}