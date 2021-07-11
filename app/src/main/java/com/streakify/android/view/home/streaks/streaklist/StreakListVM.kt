package com.streakify.android.view.home.streaks.streaklist

import androidx.lifecycle.viewModelScope
import com.streakify.android.base.BaseViewModel
import com.streakify.android.commonrepo.CommonRepository
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.utils.Logger
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.onboarding.repo.AuthRepository
import com.streakify.android.view.home.profile.data.SendFcmTokenRequest
import com.streakify.android.view.home.streaks.StreaksEvent
import com.streakify.android.view.home.streaks.streaklist.data.PunchInRequest
import com.streakify.android.view.home.streaks.streaklist.data.StreaksItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class StreakListVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val commonRepository: CommonRepository,
    private val resourceProvider: ResourceProvider,
    val localPreferences: LocalPreferences,
    val authRepository: AuthRepository
) : BaseViewModel(networkLiveData) {


    init {

    }

    fun onAttach(){
        viewModelScope.launch {
            refresh()
        }
    }

    suspend fun refresh(){
            eventListener.showLoading()
            val apiResponse = commonRepository.getStreaks()
            when(apiResponse){
                is NetworkResponse.Success -> {
                    eventListener.dismissLoading()
                    val streaklist = apiResponse.body?.body?.streaks
                    event.value = StreaksEvent.StreakListFetchedEvent(streaklist)
                }
                is NetworkResponse.ApiError -> {
                    eventListener.dismissLoading()
                    eventListener.showMessageDialog(apiResponse.error?.detail,
                        "Oops",
                        positiveClick = {
                            eventListener.dismissMessageDialog()
                        })
                }
                else -> {
                    eventListener.dismissLoading()
                }
            }
    }

    fun punch(streak: StreaksItem?, isPunchIn: Boolean) {
        viewModelScope.launch {
                eventListener.showLoading()
                val apiResponse = commonRepository.punch(
                    PunchInRequest(isPunchIn),
                    streak?.id.toString()
                )
                when (apiResponse) {
                    is NetworkResponse.Success -> {
                        eventListener.dismissLoading()
                        refresh()
                    }
                    is NetworkResponse.ApiError -> {
                        eventListener.dismissLoading()
                        eventListener.showMessageDialog(apiResponse.error?.detail,
                            "Oops",
                            positiveClick = {
                                eventListener.dismissMessageDialog()
                            })
                    }
                    else -> {
                        eventListener.dismissLoading()
                    }
                }
        }
    }

    fun sendFcmToken(token:String){
        viewModelScope.launch {
//            localPreferences.readValue(LocalPreferences.FCM_TOKEN).collect {
                Logger.log("Sending FCM Token",token)
                val apiResponse = authRepository.sendFcmToken(SendFcmTokenRequest(token))
//            }
        }
    }

}