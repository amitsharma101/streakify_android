package com.streakify.android.view.home.friends.addfriend

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.streakify.android.base.BaseViewModel
import com.streakify.android.commonrepo.CommonRepository
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.utils.Util
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.friends.FriendsEvent
import com.streakify.android.view.home.friends.addfriend.data.AddFriendRequest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddFriendVM
@Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val commonRepository: CommonRepository,
    val localPreferences: LocalPreferences,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(networkLiveData) {

    val phone = ObservableField("")

    init {

    }

    fun onAttach() {
//        viewModelScope.launch {
//            localPreferences.readValue(LocalPreferences.AUTH_TOKEN).collect { token ->
//
//            }
//        }
    }

    fun sendFriendRequest(countryCode:String,number:String) {
        viewModelScope.launch {
                eventListener.showLoading()
                val apiResponse = commonRepository.addFriend(
                    AddFriendRequest(countryCode = countryCode,mobileNumber = Util.trim(number)))
                when(apiResponse){
                    is NetworkResponse.Success -> {
                        eventListener.dismissLoading()
                        eventListener.showMessageDialog("Friend Request Sent",
                        "Success",
                        positiveClick = {
                            eventListener.dismissMessageDialog()
                            event.value = FriendsEvent.FriendRequestSentEvent
                        })
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