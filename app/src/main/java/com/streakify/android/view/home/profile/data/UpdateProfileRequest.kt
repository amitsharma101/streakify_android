package com.streakify.android.view.home.profile.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdateProfileRequest(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("profile_pic")
	val profilePic: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
