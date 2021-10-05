package com.example.solid_android.base

interface BaseActivityContract : BaseCommonContract {
    fun initViews()

    fun getLayout(): Int

    fun attachViewModelsWithViews()

}