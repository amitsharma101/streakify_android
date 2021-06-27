package com.streakify.android.view.home.streaks.editstreak

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.viewModelScope
import com.google.gson.annotations.SerializedName
import com.streakify.android.application.AppConstants
import com.streakify.android.base.BaseViewModel
import com.streakify.android.commonrepo.CommonRepository
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.AUTH_TOKEN
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.utils.extensions.Extensions.Companion.addPropertyChangedCallback
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.onboarding.repo.AuthRepository
import com.streakify.android.view.home.streaks.StreaksEvent
import com.streakify.android.view.home.streaks.editstreak.data.CreateStreakRequest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EditStreakVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider,
    val localPreferences: LocalPreferences,
    val commonRepository: CommonRepository
) : BaseViewModel(networkLiveData) {

    val sdf = SimpleDateFormat(AppConstants.APP_DATE_FORMAT)


    val streakName = ObservableField("")
    val streakType = ObservableInt(AppConstants.STREAK_TYPE_INDEFINITE).apply {
        addPropertyChangedCallback{
            if(get()==AppConstants.STREAK_TYPE_INDEFINITE)setGoalVisibility.set(View.GONE)
            else setGoalVisibility.set(View.VISIBLE)
        }
    }
    val streakMaxDays = ObservableField("")

    val setGoalVisibility = ObservableInt(View.GONE)

    val streakStartDate = sdf.format(Date())
    
    init {

    }

    fun onIndefiniteClicked() {
        event.value = StreaksEvent.IndeifiniteClickedEvent
        streakType.set(AppConstants.STREAK_TYPE_INDEFINITE)
        setGoalVisibility.set(View.GONE)
    }

    fun onDefiniteClicked() {
        event.value = StreaksEvent.DeifiniteClickedEvent
        streakType.set(AppConstants.STREAK_TYPE_DEFINITE)
        setGoalVisibility.set(View.VISIBLE)
    }

    fun save(){
        viewModelScope.launch {
            localPreferences.readValue(LocalPreferences.AUTH_TOKEN).collect { token->
                eventListener.showLoading()
                val saveStreakRequest = CreateStreakRequest(
                     name = streakName.get(),
                     type = streakType.get(),
                     maxDuration = if(streakMaxDays.get().isNullOrBlank())0 else streakMaxDays.get()?.toInt(),
                     startDate = streakStartDate
                )
                when(commonRepository.createStreak(token!!,saveStreakRequest)){
                    is NetworkResponse.Success -> {
                       eventListener.dismissLoading()
                       eventListener.showMessageDialog("Saved Successfully",
                       "Success","Ok",positiveClick = {
                           eventListener.dismissMessageDialog()
                           eventListener.closeActivity(true)
                           })
                    }
                    else -> {
                        eventListener.dismissLoading()
                    }
                }
            }
        }
    }

}