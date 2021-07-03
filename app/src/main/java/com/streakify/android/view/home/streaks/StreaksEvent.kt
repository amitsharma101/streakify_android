package com.streakify.android.view.home.streaks

import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.streaks.streakdetail.data.ParticipantsItem
import com.streakify.android.view.home.streaks.streaklist.data.StreaksItem

sealed class StreaksEvent(val sendEvent: Boolean, val name: String) : Event(sendEvent, name){
    data class StreakListFetchedEvent(val streakList: List<StreaksItem?>?) : StreaksEvent(true, "StreakListFetchedEvent")
    object DeifiniteClickedEvent : StreaksEvent(false, "DeifiniteClickedEvent")
    object IndeifiniteClickedEvent : StreaksEvent(false, "IndeifiniteClickedEvent")
    object FriendAddedEvent :StreaksEvent(false,"FriendAddedEvent")
    data class StreakDetailParticipantsFetched(val participants: List<ParticipantsItem?>?): StreaksEvent(false,"StreakDetailParticipantsFetched")
    data class DefinitePercentage(val per: Float): StreaksEvent(false,"DefinitePercentage")
}