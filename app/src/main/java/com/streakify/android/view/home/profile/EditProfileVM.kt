package com.streakify.android.view.home.profile

import com.streakify.android.base.BaseViewModel
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.onboarding.repo.AuthRepository
import javax.inject.Inject

class EditProfileVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(networkLiveData) {


    init {

    }

}