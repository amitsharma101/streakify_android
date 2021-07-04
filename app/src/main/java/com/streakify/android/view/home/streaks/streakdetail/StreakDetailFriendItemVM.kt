package com.streakify.android.view.home.streaks.streakdetail

import com.streakify.android.R
import com.streakify.android.application.AppConstants
import com.streakify.android.base.adapter.ViewModel
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.Calculator
import com.streakify.android.utils.Util
import com.streakify.android.view.home.streaks.streakdetail.data.ParticipantsItem
import java.text.SimpleDateFormat
import java.util.*

class StreakDetailFriendItemVM(participant: ParticipantsItem,streakType:Int,streakMaxDays:Int,resourceProvider: ResourceProvider) :ViewModel {

    val phone = participant.countryCode + participant.mobileNumber
    val displayName = if(participant.name.isNullOrBlank())phone else participant.name

    val streakData = Calculator.getStreakData(participant.startDate,streakType,streakMaxDays,participant.punchIn!!)

    val days = streakData.daysFinished
    val streakPercentage : Float = streakData.percentage

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