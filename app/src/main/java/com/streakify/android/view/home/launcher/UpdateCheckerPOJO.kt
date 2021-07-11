package com.streakify.android.view.home.launcher

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdateCheckerResponse(

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("body")
	val body: Body? = null
) : Parcelable

@Parcelize
data class Body(

	@field:SerializedName("action_url")
	val actionUrl: String? = null,

	@field:SerializedName("update_type")
	val updateType: Int? = null,

	@field:SerializedName("update_available")
	val updateAvailable: Boolean? = null
) : Parcelable

@Parcelize
data class UpdateCheckerRequest(

	@field:SerializedName("version_code")
	val versionCode: Int? = null
) : Parcelable
