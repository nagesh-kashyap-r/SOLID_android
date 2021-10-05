package com.example.solid_android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), BaseFragmentContract {
    protected lateinit var mViewDataBinding: ViewDataBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        initialiseViews()
        bindViewModelsWithViews()
        initViewModelObservers()
        return mViewDataBinding.root
    }

    /**
     * hides the device keyboard
     *
     */
    override fun hideKeyboard() {
        if (null != activity && activity is BaseActivity) {
            (activity as BaseActivity).hideKeyboard()
        }
    }

    /**
     * shows the device keyboard
     *
     * @param edit
     */
    override fun showKeyboard(edit: EditText) {
        if (null != activity && activity is BaseActivity) {
            (activity as BaseActivity).showKeyboard(edit)
        }
    }

    override fun showSnackBar(view: View, message: String?) {
        if (null != activity && activity is BaseActivity) {
            (activity as BaseActivity).showSnackBar(view, message)
        }
    }

    override fun showSnackBar(view: View, message: Int) {
        if (null != activity && activity is BaseActivity) {
            (activity as BaseActivity).showSnackBar(view, message)
        }
    }

    override fun showToast(message: String) {
        if (null != activity && activity is BaseActivity) {
            (activity as BaseActivity).showToast(message)
        }
    }

    override fun showToast(resId: Int) {
        if (null != activity && activity is BaseActivity) {
            (activity as BaseActivity).showToast(resId)
        }
    }
}