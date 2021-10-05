package com.example.solid_android.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class KeyboardUtil {
    companion object {

        /**
         * hides the device soft keyboard
         *
         * @param activity
         */
        fun hideSoftInput(activity: Activity) {
            var view = activity.currentFocus
            if (view == null) view = View(activity)
            val imm = activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        /**
         * shows the device soft keyboard
         *
         * @param edit
         * @param context
         */
        fun showSoftInput(edit: EditText, context: Context) {
            edit.isFocusable = true
            edit.isFocusableInTouchMode = true
            edit.requestFocus()
            val imm = context
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(edit, 0)
        }

        /**
         *
         *
         * @param context
         */
        fun toggleSoftInput(context: Context) {
            val imm = context
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }
}