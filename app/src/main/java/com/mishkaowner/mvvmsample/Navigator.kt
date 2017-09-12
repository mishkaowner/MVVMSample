package com.mishkaowner.mvvmsample

import android.content.Context
import android.content.Intent
import javax.inject.Inject

class Navigator (val context : Context) {
    fun showItemDetail(itemViewModel: ItemViewModel?) {
        context.startActivity(Intent(context, DetailActivity::class.java).putExtra("url", itemViewModel?.imageUrl?:""))
    }
}