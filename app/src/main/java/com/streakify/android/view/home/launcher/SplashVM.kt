package com.streakify.android.view.home.launcher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.streakify.android.base.BaseViewModel
import com.streakify.android.base.Event
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.onboarding.repo.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SplashVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val authRepository: AuthRepository,
    val localPreferences: LocalPreferences,
) : BaseViewModel(networkLiveData) {

    /** User Account Detection */
    val mUserAccountLD = MutableLiveData<Event<Boolean>>()

    /** Check User is Logged in or not */
    fun checkUserLoggedIn() = viewModelScope.launch {
        /* Perform Task at Background */
        withContext(Dispatchers.IO) {
            /* Get API Response */
            val dataStoreFetch = authRepository.getAuthToken()
            dataStoreFetch.collect {
                mUserAccountLD.postValue(Event(!it.isNullOrEmpty()))
            }
        }
    }
}
