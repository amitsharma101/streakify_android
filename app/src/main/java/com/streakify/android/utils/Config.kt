package com.streakify.android.utils

import android.content.SharedPreferences

object Config {

    private fun getEditor(): SharedPreferences.Editor? {
        return SessionManager.getInstance()?.getEditor()
    }

    private fun getPref(): SharedPreferences? {
        return SessionManager.getInstance()?.getSharedPreference()
    }

    fun setString(key: String, value: String) {
        getEditor()?.putString(key, value)
        getEditor()?.apply()
    }

    fun setBoolean(key: String, value: Boolean) {
        getEditor()?.putBoolean(key, value)
        getEditor()?.apply()
    }

    fun setLong(key: String, value: Long) {
        getEditor()?.putLong(key, value)
        getEditor()?.apply()
    }

    fun setFloat(key: String, value: Float) {
        getEditor()?.putFloat(key, value)
        getEditor()?.apply()
    }

    fun setInt(key: String, value: Int) {
        getEditor()?.putInt(key, value)
        getEditor()?.apply()
    }

    fun setStringSet(key: String, value: Set<String>) {
        getEditor()?.putStringSet(key, value)
        getEditor()?.apply()
    }

    fun getString(key: String, value: String): String? {
        return getPref()?.getString(key, value)
    }

    fun getBoolean(key: String, value: Boolean): Boolean? {
        return getPref()?.getBoolean(key, value)
    }

    fun getLong(key: String, value: Long): Long? {
        return getPref()?.getLong(key, value)
    }

    fun getFloat(key: String, value: Float): Float? {
        return getPref()?.getFloat(key, value)
    }

    fun getInt(key: String, value: Int): Int? {
        return getPref()?.getInt(key, value)
    }

    fun getStringSet(key: String): MutableSet<String>? {
        return getPref()?.getStringSet(key, emptySet())
    }
}