package com.example.streakify.utils


const val AUTH_TOKEN = "AUTH_TOKEN"
const val USER_ID = "USER_ID"
const val VERSION_CODE = "VERSION_CODE"
const val INDIA_COUNTRY_CODE = "+91"
const val DEVICE_ID = "DEVICE_ID"
const val COUNTRY_CODE = "COUNTRY_CODE"
const val PHONE_NO = "PHONE_NO"
const val PLATFORM = "PLATFORM"
const val LOCALE = "LOCALE"

object UserConfig {

    fun isLoggedIn(): Boolean {
        return !getAuthToken().isNullOrEmpty()
    }

    fun getLocale(): String? {
        return Config.getString(LOCALE, "en_GB")
    }

//    private fun setLocale(language: Int) {
//        Config.setString(LOCALE, KhatabookLanguage.getLocale(language))
//    }

    fun setCountryCode(code: String) {
        Config.setString(COUNTRY_CODE, code)
    }

    fun getCountryCode(): String? {
        return Config.getString(COUNTRY_CODE, INDIA_COUNTRY_CODE)
    }

    fun setPhoneNo(phone: String) {
        Config.setString(PHONE_NO, phone)
    }

    fun getPhoneNo(): String? {
        return Config.getString(PHONE_NO, "")
    }


    fun getUserId(): String? {
        return Config.getString(USER_ID, "")
    }

    fun setUserId(x: String) {
        Config.setString(USER_ID, x)
    }

    fun getDeviceId(): String? {
        return Config.getString(DEVICE_ID, "")
    }

    fun setDeviceId(x: String) {
        Config.setString(DEVICE_ID, x)
    }

    fun getAuthToken(): String? {
        return Config.getString(AUTH_TOKEN, "")
    }

    fun setAuthToken(x: String) {
        Config.setString(AUTH_TOKEN, x)
    }
}