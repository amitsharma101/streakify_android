package com.streakify.android.view.home.friends.firendslist

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
import com.streakify.android.view.home.onboarding.repo.AuthRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsListVM
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
            localPreferences.readValue(LocalPreferences.AUTH_TOKEN).collect { token ->
                refresh(token!!)
            }
        }
    }

    suspend fun refresh(token:String){
        eventListener.showLoading()
        val apiResponse = commonRepository.getFriends(token!!)
        when (apiResponse) {
            is NetworkResponse.Success -> {
                eventListener.dismissLoading()
                val friendRequest = apiResponse.body?.body?.pendingFriends
                val friends = apiResponse.body?.body?.activeFriends

                event.value = FriendsEvent.FriendsListFetchedEvent(friends)
                event.value = FriendsEvent.PendingFriendsListFetchedEvent(friendRequest)
            }
            else -> {
                eventListener.dismissLoading()
            }
        }
    }

    fun friendRequestAction(pendingFriend:PendingFriendsItem, status:Int){
        viewModelScope.launch {
            localPreferences.readValue(LocalPreferences.AUTH_TOKEN).collect { token ->
                eventListener.showLoading()
                val apiResponse = commonRepository.actionFriendRequest(
                    token!!,
                    FriendRequestActionRequest(status = status),
                    pendingFriend.id.toString()
                )
                when(apiResponse){
                    is NetworkResponse.Success -> {
                        refresh(token)
                    }
                    else -> {
                        eventListener.dismissLoading()
                    }
                }
            }
        }
    }
}