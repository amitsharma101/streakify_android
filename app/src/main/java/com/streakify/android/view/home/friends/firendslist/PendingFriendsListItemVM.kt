package com.streakify.android.view.home.friends.firendslist

import com.streakify.android.R
import com.streakify.android.base.adapter.ViewModel
import com.streakify.android.view.home.friends.firendslist.data.ActiveFriendsItem
import com.streakify.android.view.home.friends.firendslist.data.PendingFriendsItem

class PendingFriendsListItemVM constructor(
    val pendingFriend : PendingFriendsItem,
    val listener : FriendRequestAction
) : ViewModel {

    fun acceptRequest(){
        listener.accept(pendingFriend)
    }

    fun rejectRequest(){
        listener.reject(pendingFriend)
    }



    override fun getViewType(): Int {
        return R.layout.friend_request_list_item
    }
}

interface FriendRequestAction{
    fun accept(pendingFriend: PendingFriendsItem)
    fun reject(pendingFriend: PendingFriendsItem)
}