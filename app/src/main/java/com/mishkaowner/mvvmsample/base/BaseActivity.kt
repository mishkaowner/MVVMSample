package com.mishkaowner.mvvmsample.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<T1, T2 : ViewModel> : AppCompatActivity() {
    private val KEY_IS_RETAINED = "KEY_IS_RETAINED"

    @Inject lateinit var viewModel: T2

    private var mComponent: T1? = null

    abstract fun getLayoutId(): Int

    override fun onRetainCustomNonConfigurationInstance(): T1? {
        return getComponent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        inject(getComponent())
        if(!isRetained(savedInstanceState) && savedInstanceState != null) {
            val vmClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<T2>
            val value = savedInstanceState.getString(vmClass.name, "")
            viewModel = Gson().fromJson<T2>(value, vmClass)
        }
        BindingAdapters.defaultBinder.bind(binding, viewModel)
        if (!isRetained(savedInstanceState)) {
            viewModel.onBind()
        }
    }

    private fun getComponent(): T1? {
        if (null != mComponent) {
            return mComponent
        }
        val retainedObject = lastCustomNonConfigurationInstance
        mComponent = if (retainedObject != null) {
            retainedObject as T1?
        } else {
            newComponent()
        }
        return mComponent
    }

    abstract fun newComponent(): T1?

    protected abstract fun inject(component: T1?)

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(KEY_IS_RETAINED, isChangingConfigurations)
        if(!isChangingConfigurations) {
            val vmClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<T2>
            val value = Gson().toJson(viewModel)
            outState.putString(vmClass.name, value)
        }
        super.onSaveInstanceState(outState)
    }

    private fun isRetained(state: Bundle?): Boolean {
        return state != null && state.containsKey(KEY_IS_RETAINED) && state.getBoolean(KEY_IS_RETAINED)
    }
}