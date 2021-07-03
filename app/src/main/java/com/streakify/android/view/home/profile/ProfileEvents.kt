package com.streakify.android.view.home.profile

import com.streakify.android.utils.livedata.Event

sealed class ProfileEvents(val sendEvent: Boolean, val name: String) : Event(sendEvent, name){
    object ProfileUpdatedEvent : ProfileEvents(false, "ProfileUpdatedEvent")
}