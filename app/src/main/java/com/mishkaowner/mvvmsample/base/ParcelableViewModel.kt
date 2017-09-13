package com.mishkaowner.mvvmsample.base

import android.os.Bundle

abstract class ParcelableViewModel : ViewModel{
    abstract fun onRestore(bundle: Bundle)
    abstract fun onSave(bundle: Bundle)
}