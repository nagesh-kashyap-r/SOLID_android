package com.example.solid_android.base


interface BaseFragmentContract : BaseCommonContract {
    fun initialiseViews()
    fun getPageName(): Int
    fun getLayout(): Int
    fun bindViewModelsWithViews()
}