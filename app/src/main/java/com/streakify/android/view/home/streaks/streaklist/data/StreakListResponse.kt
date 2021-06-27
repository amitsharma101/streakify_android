package com.streakify.android.view.home.streaks.streaklist.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StreakListResponse(

	@field:SerializedName("details")
	val details: String? = null,

	@field:SerializedName("body")
	val body: Body? = null
) : Parcelable

@Parcelize
data class StreaksItem(

	@field:SerializedName("punch_in")
	val punchIn: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("streak_id")
	val streakId: Int? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("created_by")
	val createdBy: Int? = null,

	@field:SerializedName("max_duration")
	val maxDuration: Int? = null,

	@field:SerializedName("streak_started_from")
	val streakStartDate: String? = null,

	@field:SerializedName("user_started_from")
	val userStartDate: String? = null
) : Parcelable

@Parcelize
data class Body(

	@field:SerializedName("streak_records")
	val streaks: List<StreaksItem?>? = null
) : Parcelable

@Parcelize
data class PunchResponse(

	@field:SerializedName("detail")
	val detail: String? = null
) : Parcelable
