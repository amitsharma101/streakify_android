package com.example.streakify.view.home.launcher

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.streakify.base.BaseViewModel
import com.example.streakify.base.Event
import com.example.streakify.utils.LocalPreferences
import com.example.streakify.utils.network.NetworkLiveData
import com.example.streakify.view.dialog.common.EventListener
import com.example.streakify.view.home.onboarding.repo.AuthRepository
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
