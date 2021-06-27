package com.streakify.android.view.home.streaks.streaklist

import android.view.View
import androidx.databinding.ObservableInt
import com.streakify.android.R
import com.streakify.android.application.AppConstants
import com.streakify.android.base.adapter.ViewModel
import com.streakify.android.databinding.FragmentStreaksBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.Util
import com.streakify.android.view.home.streaks.streaklist.data.StreaksItem
import kotlinx.android.synthetic.main.streak_list_item_definite.view.*
import java.text.SimpleDateFormat
import java.util.*

class StreakListItemVM constructor(
    val streak:StreaksItem?,
    val resourceProvider: ResourceProvider,
    val listener: StreakListInterface
) :ViewModel{

    val sdf = SimpleDateFormat(AppConstants.APP_DATE_FORMAT)

    val days : Int = if(streak?.userStartDate.isNullOrBlank())0 else{
        val userStartDate = sdf.parse(streak?.userStartDate)
        userStartDate.hours = 0
        userStartDate.minutes = 0
        userStartDate.seconds = 0


        val punchedInToday = streak?.punchIn
        val today = Date()
        today.hours = 0
        today.minutes = 0
        today.seconds = 0

        var diffDays = Util.getDifferenceDays(today,userStartDate).toInt()
        diffDays = if(streak?.punchIn!!)(diffDays+1) else diffDays
        diffDays
    }

    val streakPercentage : Float = if((streak?.maxDuration)?:0 == 0)0F else{
        val nm = (days.toDouble()/ streak?.maxDuration?.toDouble()!!)*100
        nm.toFloat()
    }

    val definite_string = resourceProvider.getString(R.string.x_of_ydays_streak,days.toString(),streak?.maxDuration.toString())

    val indefinite_string = resourceProvider.getString(R.string.x_days_streak,days.toString())

    val punchInVisibility = ObservableInt(if(streak?.punchIn == true) View.GONE else View.VISIBLE)
    val punchOutVisibility = ObservableInt(if(streak?.punchIn == true) View.VISIBLE else View.GONE)

    fun punchedIn(streakItem:StreaksItem){
        listener.onPunchedIn(streakItem)
    }

    fun punchedOut(streakItem:StreaksItem){
        listener.onPunchedOut(streakItem)
    }

    override fun getViewType(): Int {
        if(streak?.type == AppConstants.STREAK_TYPE_INDEFINITE)return R.layout.streak_list_item_indefinite
        else return R.layout.streak_list_item_definite
    }
}

interface StreakListInterface{
    fun onPunchedIn(streak:StreaksItem?)
    fun onPunchedOut(streak:StreaksItem?)
}