package com.mishkaowner.mvvmsample.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<out T : ViewModel> : AppCompatActivity() {
    private val KEY_IS_RETAINED = "KEY_IS_RETAINED"

    private var viewModel: T? = null

    abstract fun getLayoutId(): Int

    private fun newViewModel(): T? {
        val vmClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        return vmClass.newInstance()
    }

    @SuppressWarnings("unchecked")
    private fun getViewModel(): T? {
        if (null != viewModel) {
            return viewModel
        }
        val retainedObject = lastCustomNonConfigurationInstance
        viewModel = if (retainedObject != null) {
            retainedObject as T
        } else {
            newViewModel()
        }
        return viewModel
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return getViewModel() ?: Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = when {
            isRetained(savedInstanceState) -> getViewModel()
            savedInstanceState != null -> {
                val vmClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
                val value = savedInstanceState.getString(vmClass.name, "")
                Gson().fromJson<T>(value, vmClass)
            }
            else -> newViewModel()
        }
        BindingAdapters.defaultBinder.bind(binding, viewModel)
        if (!isRetained(savedInstanceState)) {
            viewModel?.onBind()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val vmClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        val value = Gson().toJson(viewModel)
        outState.putString(vmClass.name, value)
        outState.putBoolean(KEY_IS_RETAINED, isChangingConfigurations)
        super.onSaveInstanceState(outState)
    }

    private fun isRetained(state: Bundle?): Boolean {
        return state != null && state.containsKey(KEY_IS_RETAINED) && state.getBoolean(KEY_IS_RETAINED)
    }
}