package com.mishkaowner.mvvmsample

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class VmTest {
    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { _ -> Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { _ -> Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun doAction_doesSomething() {
        val vm = MainViewModel()
        vm.edit.set("Hello")
        vm.result.toObservable().test().assertValue("You typed Hello")
        /*
        vm.btClicked?.run()
        vm.name.toObservable().subscribe({ println(it) })*/
    }
}