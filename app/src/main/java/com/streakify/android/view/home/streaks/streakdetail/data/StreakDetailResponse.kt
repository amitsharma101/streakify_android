package com.streakify.android.view.home.streaks.streakdetail.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StreakDetailResponse(

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("body")
	val body: Body? = null
) : Parcelable

@Parcelize
data class ParticipantsItem(

	@field:SerializedName("punch_in")
	val punchIn: Boolean? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null,

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("mobile_number")
	val mobileNumber: String? = null,

	@field:SerializedName("profile_pic")
	val profilePic: String? = null
) : Parcelable

@Parcelize
data class Body(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("created_by")
	val createdBy: Int? = null,

	@field:SerializedName("max_duration")
	val maxDuration: Int? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null,

	@field:SerializedName("streak_image")
	val streakImage: String? = null,

	@field:SerializedName("participants")
	val participants: List<ParticipantsItem?>? = null
) : Parcelable
