package com.streakify.android.view.home.streaks

import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.streaks.streaklist.data.StreaksItem

sealed class StreaksEvent(val sendEvent: Boolean, val name: String) : Event(sendEvent, name){
    data class StreakListFetchedEvent(val streakList: List<StreaksItem?>?) : StreaksEvent(true, "StreakListFetchedEvent")
    object DeifiniteClickedEvent : StreaksEvent(false, "DeifiniteClickedEvent")
    object IndeifiniteClickedEvent : StreaksEvent(false, "IndeifiniteClickedEvent")
}