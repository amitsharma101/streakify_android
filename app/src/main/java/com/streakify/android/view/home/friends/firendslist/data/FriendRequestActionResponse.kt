package com.streakify.android.view.home.friends.firendslist.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FriendRequestActionResponse(

	@field:SerializedName("detail")
	val detail: String? = null
) : Parcelable
