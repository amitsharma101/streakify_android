package com.streakify.android.utils.extensions

import android.text.TextUtils
import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.*

class ValidationUtil {


    companion object {

        // DATE TIME FORMAT
        val locale = Locale("EN", "IN")
        val utcTimeZone = TimeZone.getTimeZone("UTC")
        val appDateFormat = SimpleDateFormat("dd MMM yyyy", locale);
        val statusTimeFormat = SimpleDateFormat("hh:mm a", locale)
        val webformat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        val appDateTimeFormat = SimpleDateFormat("dd MMMM, yyyy, hh:mm a", locale)
        val webDateTimeFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a", locale)
        val webPickDeliveryTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)
        val dobFormat = SimpleDateFormat("dd/MM", locale)
        val expDateFormat = SimpleDateFormat("MM/yyyy", locale)


        fun isFieldFilled(name: String): Boolean {
            val nameFieldLength = name.length
            return nameFieldLength >= 1 && name.isNotEmpty()
        }

        fun isUserNameValid(name: String): Boolean {
            return name.isNotEmpty() && !name.contains(" ")
        }

        fun isEmailValid(email: String): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isPasswordMatch(password: String, reEnterPassword: String): Boolean {
            return reEnterPassword == password
        }

        fun isPhoneValid(phone: String): Boolean {
            return android.util.Patterns.PHONE.matcher(phone).matches() && phone.length in 8..16
        }

        fun isPartyPhoneValid(phone: String): Boolean {
            return android.util.Patterns.PHONE.matcher(phone).matches()
        }

        fun isPasswordValid(password: String): Boolean {
            return password.length in 8..16
        }

        fun isOTPValid(otp: String): Boolean {
            return otp.length in 4..6
        }

        fun isCardNumberValid(cardNumber: String): Boolean {
            return cardNumber.length in 16..19
        }

        fun convertLongToTime(time: Long?): String {
            time?.let {
                val date = Date(it)
                return statusTimeFormat.format(date)
            }?: kotlin.run {
                return ""
            }
        }
        fun convertLongToDate(dateinMills: Long): String {
            val date = Date(dateinMills)
            return appDateFormat.format(date)
        }
    }
}