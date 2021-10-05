package com.example.solid_android.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<T : ViewDataBinding>(itemView: View, private val brType: Int) :
        RecyclerView.ViewHolder(itemView) {

    private val mBinding: T = DataBindingUtil.bind(itemView)!!

    fun getBinding(): T {
        return mBinding
    }

    fun getBR(): Int {
        return brType
    }
}