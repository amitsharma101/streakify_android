package com.streakify.android.view.home.onboarding.otp

import com.streakify.android.utils.livedata.Event

sealed class OtpEvent(val sendEvent: Boolean, val name: String) : Event(sendEvent, name){
    object LoginSuccessEvent : OtpEvent(true, "LoginSuccessEvent")
}