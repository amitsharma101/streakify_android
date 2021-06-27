package com.streakify.android.view.home.friends.firendslist.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FriendRequestActionRequest(

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable
