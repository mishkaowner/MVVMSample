package com.mishkaowner.mvvmsample

import android.content.Context
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.jsoniter.JsonIterator
import com.jsoniter.output.JsonStream

class MainActivity : AppCompatActivity() {
    var viewModel : MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding: ViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState != null) {
            val sharedPreferences : SharedPreferences = getSharedPreferences("DEFAULT", Context.MODE_PRIVATE)
            val sample = Gson().fromJson<MainViewModel>(sharedPreferences.getString("MAIN", ""), MainViewModel::class.java)
            println("Restore")
            viewModel = sample
        } else {
            println("New")
            viewModel = MainViewModel()
        }
        BindingAdapters.defaultBinder.bind(binding, viewModel)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val sharedPreferences : SharedPreferences = getSharedPreferences("DEFAULT", Context.MODE_PRIVATE)
        val value = Gson().toJson(viewModel)
        sharedPreferences.edit().putString("MAIN", value).apply()
    }
}
