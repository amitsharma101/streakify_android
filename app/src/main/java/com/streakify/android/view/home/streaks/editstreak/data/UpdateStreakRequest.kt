package com.streakify.android.view.home.streaks.editstreak.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdateStreakRequest(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("friends_record")
	val friendsRecord: List<FriendsRecordItem?>? = null,

	@field:SerializedName("max_duration")
	val maxDuration: Int? = null

//	@field:SerializedName("start_date")
//	val startDate: String? = null
) : Parcelable

@Parcelize
data class FriendsRecordItem(

	@field:SerializedName("is_deleted")
	val isDeleted: Boolean? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null
) : Parcelable
