package com.streakify.android.utils

import android.util.Log
import com.streakify.android.application.AppConstants
import java.text.SimpleDateFormat
import java.util.*

class Calculator {
    companion object{

        @JvmStatic
        fun getStreakData(userStartDate:String?,
                          streakType:Int,
                          streakMaxDuration:Int,
                          punchedInToday:Boolean): StreakData{
            if(userStartDate.isNullOrBlank()){
                return StreakData(0F,streakMaxDuration,0,streakType)
            }

            val sdf = SimpleDateFormat(AppConstants.APP_DATE_FORMAT)
            val startDate = sdf.parse(userStartDate)
            startDate.hours = 0
            startDate.minutes = 0
            startDate.seconds = 0

            val now = Date()
            now.hours = 0
            now.minutes = 0
            now.seconds = 0

            val diff = now.time - startDate.time
            var noOfDays = (diff / (1000 * 60 * 60* 24)).toInt()
            if(punchedInToday)noOfDays++

            if(streakType == AppConstants.STREAK_TYPE_INDEFINITE){
                return StreakData(
                    0F,streakMaxDuration,noOfDays,streakType
                )
            }

            var percentage = ((noOfDays.toDouble()/streakMaxDuration.toDouble())*100).toFloat()
            if(percentage>100F)percentage = 100F

            return StreakData(percentage,streakMaxDuration,noOfDays,streakType)
        }

    }
}

data class StreakData(
    val percentage:Float,
    val maxDays:Int,
    val daysFinished:Int,
    val streakType:Int
)