package com.mishkaowner.mvvmsample

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class VmTest {
    @Before
    fun before(){
        RxJavaPlugins.setIoSchedulerHandler { _ -> Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { _ -> Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun doAction_doesSomething() {
        /* Given */
        val classUnderTest = MainViewModel()
        /* When */
        classUnderTest.edit.toObservable().subscribe({println("$it hahaha ${classUnderTest.result.get()}")})
        classUnderTest.changeName()
        classUnderTest.edit.set("assdff;")
        /* Then */
        println(classUnderTest.result.get())
        Assert.assertEquals(classUnderTest.name.get(), "New hello")
    }
}