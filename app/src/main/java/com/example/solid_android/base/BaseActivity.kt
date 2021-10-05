package com.example.solid_android.base

import android.os.Bundle
import android.view.Display.FLAG_SECURE
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.solid_android.util.KeyboardUtil
import com.example.solid_android.util.printDisplayDensity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity(), BaseActivityContract {
    protected lateinit var mViewDataBinding: ViewDataBinding
    protected var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
        super.onCreate(savedInstanceState)
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayout())
        initViews()
        attachViewModelsWithViews()
        initViewModelObservers()

        hideSystemUI()

        window.decorView
                .setOnSystemUiVisibilityChangeListener { visibility ->
                    if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                        hideSystemUI()
                    }
                }
        printDisplayDensity(this)
        window.setFlags(FLAG_SECURE, FLAG_SECURE)
    }

    override fun hideKeyboard() {
        KeyboardUtil.hideSoftInput(this@BaseActivity)
    }

    override fun showKeyboard(edit: EditText) {
        KeyboardUtil.showSoftInput(edit, this@BaseActivity)
    }

    override fun showSnackBar(view: View, message: String?) {
        message?.let { Snackbar.make(view, message, Snackbar.LENGTH_LONG).show() }
    }

    override fun showSnackBar(view: View, message: Int) {
        Snackbar.make(
                view,
                applicationContext.resources.getString(message),
                Snackbar.LENGTH_LONG
        ).show()
    }

    override fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(resId: Int) {
        Toast.makeText(
                applicationContext,
                applicationContext.resources.getString(resId),
                Toast.LENGTH_SHORT
        ).show()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}