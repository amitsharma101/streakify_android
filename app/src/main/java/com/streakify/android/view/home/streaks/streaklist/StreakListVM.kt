package com.streakify.android.view.home.streaks.streaklist

import androidx.lifecycle.viewModelScope
import com.streakify.android.base.BaseViewModel
import com.streakify.android.commonrepo.CommonRepository
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.onboarding.repo.AuthRepository
import com.streakify.android.view.home.streaks.StreaksEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class StreakListVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val commonRepository: CommonRepository,
    private val resourceProvider: ResourceProvider,
    val localPreferences: LocalPreferences
) : BaseViewModel(networkLiveData) {


    init {

    }

    fun onAttach(){
        viewModelScope.launch {
            refresh()
        }
    }

    suspend fun refresh(){
        localPreferences.readValue(LocalPreferences.AUTH_TOKEN).collect { token->
            eventListener.showLoading()
            val apiResponse = commonRepository.getStreaks(token!!)
            when(apiResponse){
                is NetworkResponse.Success -> {
                    eventListener.dismissLoading()
                    val streaklist = apiResponse.body?.body?.streaks
                    event.value = StreaksEvent.StreakListFetchedEvent(streaklist)
                }
                else -> {
                    eventListener.dismissLoading()
                }
            }
        }
    }

}