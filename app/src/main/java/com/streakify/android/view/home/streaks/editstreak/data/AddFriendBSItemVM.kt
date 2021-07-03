package com.streakify.android.view.home.streaks.editstreak.data

import com.streakify.android.R
import com.streakify.android.base.adapter.ViewModel
import com.streakify.android.view.home.friends.firendslist.data.ActiveFriendsItem

class AddFriendBSItemVM(
    val friend:ActiveFriendsItem
) : ViewModel {


    val phone = friend.countryCode+friend.mobileNumber

    val displayName = if(friend.name.isNullOrBlank())phone else friend.name

    override fun getViewType(): Int {
        return R.layout.bs_friend_item
    }


}