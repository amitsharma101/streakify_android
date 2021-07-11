package com.streakify.android.view.home.profile

import android.content.pm.PackageInfo
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.streakify.android.application.App
import com.streakify.android.base.BaseViewModel
import com.streakify.android.commonrepo.CommonRepository
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.onboarding.repo.AuthRepository
import com.streakify.android.view.home.profile.data.MyProfile
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProfileDetailVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val commonRepository: CommonRepository,
    val localPreferences: LocalPreferences,
    val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(networkLiveData) {

    val name = ObservableField("")
    val email = ObservableField("")
    val phone = ObservableField("")
    val profilePic = ObservableField<String?>()

    val version = ObservableField("")

    var profile : MyProfile? = null

    init {

    }

    fun onAttach(){
        viewModelScope.launch {
            refresh()
        }
    }

    suspend fun refresh(){
            eventListener.showLoading()
            val apiResponse = commonRepository.getProfile()
            when(apiResponse){
                is NetworkResponse.Success -> {
                    eventListener.dismissLoading()
                    profile = apiResponse.body?.body
                    name.set(profile?.name)
                    email.set(profile?.email)
                    phone.set(profile?.countryCode+profile?.mobileNumber)
                    profilePic.set(profile?.profilePic)

                    event.value = ProfileEvents.ProfilePicEvent(profile?.profilePic)
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

    fun logout(){
        eventListener.showMessageDialog(
            title = "Streakify",
            message = "Are you sure you want to logout?",
            positiveClickTitle = "Yes",
            negativeClickTitle = "No",
            positiveClick = {
                eventListener.dismissMessageDialog()
                viewModelScope.launch {
                    authRepository.logout()
                    event.value = ProfileEvents.LogoutEvent
                }
            },
            negativeClick = {
                eventListener.dismissMessageDialog()
            }
        )
    }
}