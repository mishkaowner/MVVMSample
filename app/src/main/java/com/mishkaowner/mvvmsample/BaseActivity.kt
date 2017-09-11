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
    private var viewModel : T? = null

    abstract fun getLayoutId() : Int

    private fun createViewModel() : T?{
        val vmClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        return vmClass.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = if (savedInstanceState != null) {
            val vmClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
            val value = savedInstanceState.getString(vmClass.name, "")
            Gson().fromJson<T>(value, vmClass)
        } else {
            println("Create!")
            createViewModel()
        }
        BindingAdapters.defaultBinder.bind(binding, viewModel)
        viewModel?.onBind()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val vmClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        val value = Gson().toJson(viewModel)
        outState.putString(vmClass.name, value)
        super.onSaveInstanceState(outState)
    }
}