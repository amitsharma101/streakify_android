package com.streakify.android.utils

import okhttp3.internal.trimSubstring
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class Util {
    companion object{
        fun getDifferenceDays(d1: Date, d2: Date): Long {
            val diff: Long = d2.getTime() - d1.getTime()
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        }

        fun getHash(id: String): Long {
            val upper = id.hashCode().toLong() shl 32
            val lower = reverse(id).hashCode().toLong() - Int.MIN_VALUE.toLong()
            return upper + lower
        }

        fun reverse(str: String): String {
            val sb = StringBuilder()
            for (i in str.length - 1 downTo 0) {
                sb.append(str[i])
            }
            return sb.toString()
        }

        fun purifyMobileNumber(number: String): String {
            var number = number
            if (isBlank(number)) return number
            val m = Pattern.compile("[^0-9+]").matcher(number)
            number = m.replaceAll("")

            // removing 0 (don't allow it at start of number for INDIA ONLY)
            if (number.length > 10) {
                if (number.substring(0, 1).equals("0", ignoreCase = true)) {
                    number = number.substring(1)
                }
            }
            if (number.length > 10) {
                // assume that it to contain either 0 or country code(91 or +91)
                if (!number.startsWith("+")) {
                    val fakeNumber = "+$number" //add + to make it +91xxxx (from 91xxxx)
                    // so that removedCountryCodeFromMobileNo works
                    val newNumber = removedCountryCodeFromMobileNo(fakeNumber)
                    return if (newNumber != fakeNumber) {
                        // country code removed successfully
                        newNumber
                    } else {
                        // nothing changed, so just return original number
                        number
                    }
                }
            }
            return removedCountryCodeFromMobileNo(number)
        }

        private fun removedCountryCodeFromMobileNo(number: String): String {
            for (country in Countries().getCountryList()) {
                val countryCode: String = country.code
                if (number.startsWith(countryCode)) {
                    return number.substring(countryCode.length)
                }
            }
            return number
        }

        fun isBlank(str: String?): Boolean {
            return str == null || str.trim { it <= ' ' } == ""
        }

        fun trim(text:String):String{
            var result = ""
            val tokens = text.split(" ")
            tokens.forEach {
                result += it
            }
            return result
        }
    }
}