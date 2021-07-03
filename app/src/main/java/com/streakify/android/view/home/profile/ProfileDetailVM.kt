package com.streakify.android.view.home.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.streakify.android.base.BaseViewModel
import com.streakify.android.commonrepo.CommonRepository
import com.streakify.android.di.module.NetworkModule
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.onboarding.repo.AuthRepository
import com.streakify.android.view.home.profile.data.MyProfile
import com.streakify.android.view.home.profile.data.UpdateProfileRequest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileDetailVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val commonRepository: CommonRepository,
    val localPreferences: LocalPreferences,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(networkLiveData) {


    val name = ObservableField("")
    val email = ObservableField("")
    val phone = ObservableField("")

    var profile : MyProfile? = null

    init {

    }

    fun onAttach(){
        viewModelScope.launch {
            refresh()
        }
    }

    suspend fun refresh(){
        localPreferences.readValue(LocalPreferences.AUTH_TOKEN).collect {token->
            eventListener.showLoading()
            val apiResponse = commonRepository.getProfile(token!!)
            when(apiResponse){
                is NetworkResponse.Success -> {
                    eventListener.dismissLoading()
                    profile = apiResponse.body?.body
                    name.set(profile?.name)
                    email.set(profile?.email)
                    phone.set(profile?.countryCode+profile?.mobileNumber)
                }
                else -> {
                    eventListener.dismissLoading()
                }
            }
        }
    }
}