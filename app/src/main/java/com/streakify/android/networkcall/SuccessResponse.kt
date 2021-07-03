package com.streakify.android.networkcall

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuccessResponse(

	@field:SerializedName("detail")
	val detail: String? = null
) : Parcelable
