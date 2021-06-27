package com.streakify.android.view.home.friends

import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.friends.firendslist.data.ActiveFriendsItem
import com.streakify.android.view.home.friends.firendslist.data.PendingFriendsItem

sealed class FriendsEvent(val sendEvent: Boolean, val name: String) : Event(sendEvent, name){
    data class FriendsListFetchedEvent(val friendsList:List<ActiveFriendsItem?>?) : FriendsEvent(true, "FriendsListFetchedEvent")
    data class PendingFriendsListFetchedEvent(val pendingFriendsList:List<PendingFriendsItem?>?) : FriendsEvent(true, "PendingFriendsListFetchedEvent")
}