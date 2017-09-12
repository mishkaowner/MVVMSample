package com.mishkaowner.mvvmsample.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<V, T : ViewModel> : AppCompatActivity() {
    private val KEY_IS_RETAINED = "KEY_IS_RETAINED"

    @Inject lateinit var viewModel: T

    private var mComponent: V? = null

    abstract fun getLayoutId(): Int

    private fun newViewModel(): T? {
        val vmClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        return vmClass.newInstance()
    }

    /*@SuppressWarnings("unchecked")
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
    }*/

    override fun onRetainCustomNonConfigurationInstance(): V? {
        return getComponent()//getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        inject(getComponent())
        /*viewModel = when {
            isRetained(savedInstanceState) -> getViewModel()
            savedInstanceState != null -> {
                val vmClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
                val value = savedInstanceState.getString(vmClass.name, "")
                Gson().fromJson<T>(value, vmClass)
            }
            else -> newViewModel()
        }*/
        BindingAdapters.defaultBinder.bind(binding, viewModel)
        if (!isRetained(savedInstanceState)) {
            viewModel.onBind()
        }
    }

    private fun getComponent(): V? {
        if (null != mComponent) {
            return mComponent
        }
        val retainedObject = lastCustomNonConfigurationInstance
        mComponent = if (retainedObject != null) {
            retainedObject as V?
        } else {
            newComponent()
        }
        return mComponent
    }

    abstract fun newComponent() : V?

    protected abstract fun inject(component: V?)

    override fun onSaveInstanceState(outState: Bundle) {
        /*val vmClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        val value = Gson().toJson(viewModel)
        outState.putString(vmClass.name, value)*/
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_IS_RETAINED, isChangingConfigurations)
    }

    private fun isRetained(state: Bundle?): Boolean {
        return state != null && state.containsKey(KEY_IS_RETAINED) && state.getBoolean(KEY_IS_RETAINED)
    }
}