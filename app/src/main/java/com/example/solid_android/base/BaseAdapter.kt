package com.example.solid_android.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : ViewDataBinding>(private val list: MutableList<BaseViewModel>) : RecyclerView.Adapter<BaseViewHolder<ViewDataBinding>>() {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            position: Int
    ): BaseViewHolder<ViewDataBinding> {
        return BaseViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        getItemLayout(position),
                        parent,
                        false
                ), getItemBR(position)
        )
    }

    fun getItemList() : MutableList<BaseViewModel> {
        return list
    }

    abstract fun getItemLayout(viewType: Int): Int

    abstract fun getItemBR(viewType: Int): Int

    override fun getItemCount(): Int {
        return if (list.isNullOrEmpty()) 0 else list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        holder.getBinding().setVariable(holder.getBR(), list[position])
        holder.getBinding().executePendingBindings()
    }

    fun updateList(list: MutableList<BaseViewModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}