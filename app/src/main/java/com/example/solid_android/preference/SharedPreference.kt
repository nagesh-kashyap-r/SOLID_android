package com.example.solid_android.preference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.lang.reflect.Type

class SharedPreference private constructor(context: Context) {
    companion object {
        private const val PREFS = ""

        private var mSharedPreferences: SharedPreferences? = null
        private var mEditor: SharedPreferences.Editor? = null

        @Volatile
        private var instance: SharedPreference? = null

        fun getInstance(context: Context): SharedPreference =
                instance ?: synchronized(this) {
                    instance ?: SharedPreference(context)
                }
    }

    annotation class Keys {
        companion object {
            const val SP_USER_TOKEN = "SP_USER_TOKEN"
            const val SP_LOGGED_IN_USER = "SP_LOGGED_IN_USER"
        }
    }

    init {
        mSharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences?.edit()
        mEditor?.apply()
    }

    private fun getInteger(@Keys key: String, defaultVal: Int): Int {
        return mSharedPreferences?.getInt(key, defaultVal) ?: 0
    }

    private fun putInteger(@Keys key: String, defaultVal: Int) {
        mEditor?.putInt(key, defaultVal)?.apply()
    }

    private fun getBoolean(@Keys key: String, defaultVal: Boolean): Boolean {
        return mSharedPreferences?.getBoolean(key, defaultVal) ?: false
    }

    private fun putBoolean(@Keys key: String, defaultVal: Boolean) {
        mEditor?.putBoolean(key, defaultVal)?.apply()
    }

    private fun getString(@Keys key: String, defaultVal: String?): String {
        return mSharedPreferences?.getString(key, defaultVal) ?: ""
    }

    private fun putString(@Keys key: String, value: String?) {
        mEditor?.putString(key, value)?.apply()
    }

    private fun putStringCommit(@Keys key: String, value: String?) {
        mEditor?.putString(key, value)?.commit()
    }

    private fun getLong(@Keys key: String, defaultVal: Long): Long {
        return mSharedPreferences?.getLong(key, defaultVal) ?: 0L
    }

    private fun putLong(@Keys key: String, value: Long) {
        mEditor?.putLong(key, value)?.apply()
    }

    private fun getFloat(@Keys key: String, defaultVal: Float): Float {
        return mSharedPreferences?.getFloat(key, defaultVal) ?: 0.0F
    }

    private fun putFloat(@Keys key: String, value: Float) {
        mEditor?.putFloat(key, value)?.apply()
    }

    private fun putObject(@Keys key: String, value: Any) {
        mEditor?.putString(key, Gson().toJson(value))?.apply()
    }

    private fun <T> getObject(@Keys key: String, typeOfObject: Class<T>): T? {
        val value = getString(key, null)
        return Gson().fromJson(value, typeOfObject)
    }

    private fun getObject(@Keys key: String, type: Type): Any? {
        val value = getString(key, null)
        return Gson().fromJson<Any>(value, type)
    }

    private fun containsKey(key: String): Boolean {
        return mSharedPreferences?.contains(key) ?: false
    }

    fun putUserToken(token: String) {
        putString(Keys.SP_USER_TOKEN, token)
    }

    fun saveLoggedInUser() {
        putBoolean(Keys.SP_LOGGED_IN_USER, true)
    }

    fun isLoggedInUser(): Boolean {
        return mSharedPreferences?.getBoolean(Keys.SP_LOGGED_IN_USER, false) ?: false
    }
}
