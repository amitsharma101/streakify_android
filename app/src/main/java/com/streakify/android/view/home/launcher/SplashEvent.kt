package com.streakify.android.view.home.launcher

import com.streakify.android.utils.livedata.Event

sealed class SplashEvent(sendEvent: Boolean, val name: String) : Event(sendEvent, name) {
    data class UpdateInfoFetchedEvent(val updateCheckerResponse: UpdateCheckerResponse) : SplashEvent(false, "UpdateInfoFetchedEvent")
}