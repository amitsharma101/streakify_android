package com.streakify.android.view.home.streaks.streakdetail

import com.streakify.android.R
import com.streakify.android.application.AppConstants
import com.streakify.android.base.adapter.ViewModel
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.Util
import com.streakify.android.view.home.streaks.streakdetail.data.ParticipantsItem
import java.text.SimpleDateFormat
import java.util.*

class StreakDetailFriendItemVM(participant: ParticipantsItem,streakType:Int,streakMaxDays:Int,resourceProvider: ResourceProvider) :ViewModel {

    val phone = participant.countryCode + participant.mobileNumber
    val displayName = if(participant.name.isNullOrBlank())phone else participant.name

    val sdf = SimpleDateFormat(AppConstants.APP_DATE_FORMAT)

    val days : Int = if(participant.startDate.isNullOrBlank())0 else{
        val userStartDate = sdf.parse(participant.startDate)
        userStartDate.hours = 0
        userStartDate.minutes = 0
        userStartDate.seconds = 0


        val punchedInToday = participant?.punchIn
        val today = Date()
        today.hours = 0
        today.minutes = 0
        today.seconds = 0

        var diffDays = Util.getDifferenceDays(today,userStartDate).toInt()
        diffDays = if(participant?.punchIn!!)(diffDays+1) else diffDays
        diffDays
    }

    val streakPercentage : Float = if((streakMaxDays)?:0 == 0)0F else{
        val nm = (days.toDouble()/ streakMaxDays?.toDouble()!!)*100
        nm.toFloat()
    }

    val displayString = when(streakType){
        AppConstants.STREAK_TYPE_DEFINITE -> {
            resourceProvider.getString(R.string.x_of_ydays_streak,days.toString(),streakMaxDays.toString())
        }
        AppConstants.STREAK_TYPE_INDEFINITE -> {
            resourceProvider.getString(R.string.x_days_streak,days.toString())
        }
        else -> {
            ""
        }
    }

    override fun getViewType(): Int {
        return R.layout.streakdetail_friends_item
    }
}