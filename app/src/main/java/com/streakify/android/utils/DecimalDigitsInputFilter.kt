package com.streakify.android.utils

import android.text.InputFilter
import android.text.Spanned

/**
 * applies filter on edit texts to accept decimal values only upto specified digits
 * @param decimalDigits is the number of max digits to be accepted after decimal
 */
class DecimalDigitsInputFilter(private val decimalDigits: Int) : InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        var dotPos = -1
        val len = dest.length
        for (i in 0 until len) {
            val c = dest[i]
            if (c == '.') {
                dotPos = i
                break
            }
        }
        if (dotPos >= 0) {
            // protects against many dots
            if (source == ".") {
                return ""
            }
            // if the text is entered before the dot
            if (dend <= dotPos) {
                return null
            }
            if (len - dotPos > decimalDigits) {
                return ""
            }
        }
        return null
    }

}