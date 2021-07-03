package com.streakify.android.view.home.streaks.streaklist.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PunchInRequest(

	@field:SerializedName("punch_in")
	val punchIn: Boolean? = null
) : Parcelable
