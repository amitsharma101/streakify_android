package com.streakify.android.view.home.friends.addfriend.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddFriendRequest(

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("mobile_number")
	val mobileNumber: String? = null
) : Parcelable
