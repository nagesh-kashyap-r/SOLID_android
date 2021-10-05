package com.example.solid_android.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

class AbstractBaseAdapter(private val list: MutableList<BaseViewModel>, @LayoutRes val resId: Int, private val bindingRes: Int) :
        BaseAdapter<ViewDataBinding>(list) {

    override fun getItemLayout(viewType: Int): Int {
        return resId
    }

    override fun getItemBR(viewType: Int): Int {
        return bindingRes
    }

}