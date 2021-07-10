package com.streakify.android.utils

import android.util.Log
import com.google.gson.Gson
import com.streakify.android.BuildConfig

object Logger {

    private fun getDefaultTag() = "Logger"
    fun isLoggingEnabled() = BuildConfig.BUILD_TYPE == "debug"

    fun log(debugLine: String) {
        log(getDefaultTag(), debugLine)
    }

    fun log(tag: String = getDefaultTag(), debugLine: String) {
        if (!isLoggingEnabled()) return
        Log.d(tag, debugLine)
    }

    fun logJsonString(tag:String = getDefaultTag(), anyObject:Any?, gson: Gson? = null ) {
        //TODO currently its crashing if anyObject is collection
        if(!isLoggingEnabled()) return
        if(anyObject == null) {
            Log.d(tag, " object value is null")
            return
        }
        var gson = gson
        if(gson == null) gson = Gson()
        val jsonString = gson.toJson(anyObject)
        Log.d(tag, "${anyObject.javaClass.simpleName} $jsonString")
    }

    fun error(debugLine: String) {
        if (!isLoggingEnabled()) return
        Log.e(getDefaultTag(), debugLine)
    }
}

//TODO
class ExceptionLoggerUtil {
    companion object {
        fun logException(throwable: Throwable) {
//            FirebaseCrashlytics.getInstance().recordException(throwable)
        }

        fun log(message: String) {
//            FirebaseCrashlytics.getInstance().log(message)
        }
    }
}