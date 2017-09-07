package com.mishkaowner.mvvmsample

object ViewProviders{
    @JvmStatic
    fun getItemListing(): ViewProvider {
        return object : ViewProvider {
            override fun getView(vm: ViewModel): Int {
                /*if (vm is ItemViewModel) {
                    return if (vm.hasImage()) R.layout.item_with_image else R.layout.item_with_image
                }
                return 0*/
                return R.layout.item_with_image
            }
        }
    }
}