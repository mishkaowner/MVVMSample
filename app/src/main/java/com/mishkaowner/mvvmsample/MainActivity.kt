package com.mishkaowner.mvvmsample

import android.app.Activity
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.mishkaowner.mvvmsample.base.BaseActivity
import com.mishkaowner.mvvmsample.base.Transitionable
import com.mishkaowner.mvvmsample.base.ViewModel
import com.mishkaowner.mvvmsample.di.MainComponent
import com.mishkaowner.mvvmsample.di.MainModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.internal.operators.completable.CompletableFromAction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Transitionable, LifecycleRegistryOwner {
    override fun getLifecycle(): LifecycleRegistry {
        return registry
    }

    val registry = LifecycleRegistry(this)

    private val db by lazy { UsersDatabase.getInstance(this).userDao() }

    override fun getTransitionalContext(): Activity? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransitionalViews(pos: Int): Array<Pair<View, String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val viewModel by lazy { ViewModelProviders.of(this).get(AACVM::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CompletableFromAction(Action {
            db.insertUser(User("1", "sdaf"))
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        db.getAllUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ println("Yser is ${it.size}") })
        viewModel?.varA?.value = 2

        if (savedInstanceState == null) {
            println("First ${viewModel?.varA?.value}")
        } else {
            println("Restored ${viewModel?.varA?.value}")
        }

        viewModel?.varA?.observe(this, Observer {
            resultText.text = it.toString()
        })

        resultText.setOnClickListener {
            CompletableFromAction(Action {
                //viewModel?.varA?.value = viewModel?.varA?.value?:0 + 1
                value++
                //db.insertUser(User("${value}", "sdaf ${value}"))
                db.deleteUser(User("1", ""))
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({""})
            println("ONClicked ${viewModel?.varA}")

        }
    }

    private var value = 2
}
/*

class MainActivity : BaseActivity<MainComponent, MainViewModel>(), Transitionable {
    override fun getTransitionalViews(pos: Int): Array<Pair<View, String>> {
        val list = arrayListOf<Pair<View, String>>()
        val selctedView : ViewGroup = recyclerView.findViewHolderForAdapterPosition(pos).itemView as ViewGroup
        (0 until selctedView.childCount).forEach {
            val v = selctedView.getChildAt(it)
            if(v != null && v.transitionName != null) {
                list.add(Pair(v, v.transitionName))
            }
        }
        return list.toTypedArray()
    }

    override fun getTransitionalContext(): Activity {
        return this
    }

    override fun newComponent(): MainComponent? {
        return MyApp.mainAppComponent.plus(MainModule(this))
    }

    override fun inject(component: MainComponent?) {
        component?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}*/
