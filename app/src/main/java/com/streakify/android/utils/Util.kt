package com.streakify.android.utils

import java.util.*
import java.util.concurrent.TimeUnit

class Util {
    companion object{
        fun getDifferenceDays(d1: Date, d2: Date): Long {
            val diff: Long = d2.getTime() - d1.getTime()
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        }
    }
}