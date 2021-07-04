package com.streakify.android.view.home.friends.firendslist

import com.streakify.android.R
import com.streakify.android.base.adapter.ViewModel
import com.streakify.android.view.home.friends.firendslist.data.ActiveFriendsItem

class FriendsListItemVM constructor(
    val activeFriend : ActiveFriendsItem,
    val listener:FriendsListInterface
) : ViewModel {

    fun removeFriend(activeFriend : ActiveFriendsItem){
        listener.onFriendRemove(activeFriend)
    }


    override fun getViewType(): Int {
        return R.layout.friends_list_item
    }
}

interface FriendsListInterface{
    fun onFriendRemove(activeFriend: ActiveFriendsItem)
}