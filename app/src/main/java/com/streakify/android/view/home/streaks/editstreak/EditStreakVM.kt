package com.streakify.android.view.home.streaks.editstreak

import com.streakify.android.base.BaseViewModel
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.onboarding.repo.AuthRepository
import javax.inject.Inject

class EditStreakVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(networkLiveData) {


    init {

    }

    fun onIndefiniteClicked() {
        event.value = AddItemEvent.ProductClickedEvent
    }

    fun onDefiniteClicked() {
        event.value = AddItemEvent.ServicesClickedEvent
    }

}