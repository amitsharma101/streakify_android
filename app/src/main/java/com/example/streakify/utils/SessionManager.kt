package com.example.streakify.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.streakify.application.App

class SessionManager private constructor(context: Context) {

    private var pref: SharedPreferences? = null

    fun getSharedPreference() = pref

    private var editor: SharedPreferences.Editor? = null

    fun getEditor() = editor

    init {
        pref = context.getSharedPreferences("Streakify", 0)
        editor = pref?.edit()
    }

    companion object {
        private var sessionManager: SessionManager? = null

        fun getInstance(context: Context?): SessionManager? {
            context ?: return sessionManager
            if (sessionManager == null) sessionManager = SessionManager(context)
            return sessionManager
        }

        fun getInstance(): SessionManager? {
            val context = App.getApplicationContext()
            return getInstance(context)
        }
    }

}