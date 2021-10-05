package com.example.solid_android.base

import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes

interface BaseCommonContract {

    fun hideKeyboard()

    fun showKeyboard(edit: EditText)

    fun showSnackBar(view: View, message: String?)

    fun showSnackBar(view: View, message: Int)

    fun showToast(message: String)

    fun showToast(@StringRes resId: Int)

    fun initViewModelObservers()
}