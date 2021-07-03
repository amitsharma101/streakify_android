package com.streakify.android.view.home.streaks.editstreak

import com.streakify.android.R
import com.streakify.android.base.adapter.ViewModel
import com.streakify.android.view.home.friends.firendslist.data.ActiveFriendsItem

class AddedFriendEditStreakItemVM(
    val friend:ActiveFriendsItem,
    val listener:AddedFriendInterface
):ViewModel {

    val phone = friend.countryCode+friend.mobileNumber
    val displayName = if(friend.name.isNullOrBlank())phone else friend.name

    override fun getViewType(): Int {
        return R.layout.edit_streak_friend
    }
}

interface AddedFriendInterface{
    fun onRemoveClicked(value:ActiveFriendsItem)
}