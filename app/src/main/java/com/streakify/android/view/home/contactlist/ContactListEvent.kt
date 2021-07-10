package com.streakify.android.view.home.contactlist

import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.contactlist.data.Contact

sealed class ContactListEvent(sendEvent: Boolean, val name: String) : Event(sendEvent, name) {
    data class ContactListClick(val contact: Contact) : ContactListEvent(false, "ContactListClick")
    object OnPermissionSettingClick : ContactListEvent(false, "OnPermissionSettingClick")
    object OnSkipClick : ContactListEvent(false, "OnSkipClick")
    object AddFriendManuallyClicked : ContactListEvent(false, "AddFriendManuallyClicked")
}