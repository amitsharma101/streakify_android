package com.streakify.android.view.home.streaks.streaklist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Streak(

	@field:SerializedName("punch_in")
	val punchIn: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("created_by")
	val createdBy: Int? = null,

	@field:SerializedName("max_duration")
	val maxDuration: Int? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null
) : Parcelable
