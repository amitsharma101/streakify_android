package com.streakify.android.view.home.streaks.streaklist

import com.streakify.android.R
import com.streakify.android.application.AppConstants
import com.streakify.android.base.adapter.ViewModel
import com.streakify.android.databinding.FragmentStreaksBinding
import kotlinx.android.synthetic.main.streak_list_item_definite.view.*

class StreakListItemVM constructor(
    val streak:Streak
) :ViewModel{

    val definite_string = "90/100 days streak"

    val indefinite_string = "45 days streak"



    override fun getViewType(): Int {
        if(streak.type == AppConstants.STREAK_TYPE_INDEFINITE)return R.layout.streak_list_item_indefinite
        else return R.layout.streak_list_item_definite
    }
}