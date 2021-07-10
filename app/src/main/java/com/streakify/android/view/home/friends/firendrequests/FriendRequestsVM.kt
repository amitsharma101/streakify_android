package com.streakify.android.view.home.friends.firendrequests

import androidx.lifecycle.viewModelScope
import com.streakify.android.base.BaseViewModel
import com.streakify.android.commonrepo.CommonRepository
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.friends.FriendsEvent
import com.streakify.android.view.home.friends.firendslist.data.FriendRequestActionRequest
import com.streakify.android.view.home.friends.firendslist.data.PendingFriendsItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendRequestsVM
@Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val commonRepository: CommonRepository,
    val localPreferences: LocalPreferences,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(networkLiveData) {


    init {

    }

    fun onAttach() {
        viewModelScope.launch {
            refresh()
        }
    }

    suspend fun refresh(){
        eventListener.showLoading()
        val apiResponse = commonRepository.getFriends()
        when (apiResponse) {
            is NetworkResponse.Success -> {
                eventListener.dismissLoading()
                val friendRequest = apiResponse.body?.body?.pendingFriends

                event.value = FriendsEvent.PendingFriendsListFetchedEvent(friendRequest)
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

    fun friendRequestAction(pendingFriend: PendingFriendsItem, status:Int){
        viewModelScope.launch {
                eventListener.showLoading()
                val apiResponse = commonRepository.actionFriendRequest(
                    FriendRequestActionRequest(status = status),
                    pendingFriend.id.toString()
                )
                when(apiResponse){
                    is NetworkResponse.Success -> {
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
}