package com.streakify.android.view.home.onboarding.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetTokenRequest(

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("mobile_number")
	val mobileNumber: String? = null,

	@field:SerializedName("firebase_token")
	val firebaseToken: String? = null
) : Parcelable
