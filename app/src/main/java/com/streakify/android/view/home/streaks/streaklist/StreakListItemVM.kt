package com.streakify.android.view.home.streaks.streaklist

import android.content.Context
import android.view.View
import androidx.databinding.ObservableInt
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.streakify.android.R
import com.streakify.android.application.AppConstants
import com.streakify.android.base.adapter.ViewModel
import com.streakify.android.databinding.FragmentStreaksBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.Calculator
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

    val streakData = Calculator.getStreakData(streak?.userStartDate,streak?.type!!,streak?.maxDuration?:0,streak?.punchIn!!)

    val days = streakData.daysFinished
    val streakPercentage : Float = streakData.percentage

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