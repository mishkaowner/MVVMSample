package com.mishkaowner.mvvmsample

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<T : ViewModel> : AppCompatActivity() {
    val prefName : String = "MVVM"
    var viewModel : T? = null

    abstract fun getLayoutId() : Int

    fun createViewModel() : T?{
        val vmClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        viewModel = vmClass.newInstance()
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        if (savedInstanceState != null) {
            val vmClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
            val sharedPreferences : SharedPreferences = getSharedPreferences(prefName, Context.MODE_PRIVATE)
            val value = sharedPreferences.getString(vmClass.name, "")
            viewModel = Gson().fromJson<T>(value, vmClass)
        } else {
            viewModel = createViewModel()
        }
        BindingAdapters.defaultBinder.bind(binding, viewModel)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val vmClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        val sharedPreferences : SharedPreferences = getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val value = Gson().toJson(viewModel)
        sharedPreferences.edit().putString(vmClass.name, value).apply()
    }
}