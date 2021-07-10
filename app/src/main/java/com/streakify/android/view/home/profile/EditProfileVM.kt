package com.streakify.android.view.home.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.streakify.android.base.BaseViewModel
import com.streakify.android.commonrepo.CommonRepository
import com.streakify.android.di.module.NetworkModule
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.utils.extensions.Extensions.Companion.addPropertyChangedCallback
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.onboarding.repo.AuthRepository
import com.streakify.android.view.home.profile.data.MyProfile
import com.streakify.android.view.home.profile.data.UpdateProfileRequest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditProfileVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val commonRepository: CommonRepository,
    val localPreferences: LocalPreferences,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(networkLiveData) {

    var profile = ObservableField<MyProfile>().apply {
        addPropertyChangedCallback {
            if(get()!=null){
                val pro = get()
                name.set(pro?.name)
                email.set(pro?.email)
                phone.set(pro?.mobileNumber)
            }
        }
    }


    val name = ObservableField("")
    val email = ObservableField("")
    val phone = ObservableField("")

    init {

    }

    fun onAttach(){

    }



    fun updateProfile(){
        viewModelScope.launch {
                eventListener.showLoading()
                val req = UpdateProfileRequest(
                    name = name.get(),
                    email = email.get()
                )
            val apiResponse = commonRepository.updateProfile(req)
                when(apiResponse){
                    is NetworkResponse.Success -> {
                        event.value = ProfileEvents.ProfileUpdatedEvent
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

}