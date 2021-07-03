package com.streakify.android.view.home.streaks.editstreak.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateStreakRequest(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("max_duration")
	val maxDuration: Int? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null,

	@field:SerializedName("friends")
	val friends: List<Int>? = null
) : Parcelable
