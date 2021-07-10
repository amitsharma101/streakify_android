package com.streakify.android.view.home.image

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UploadImageResponse(

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("body")
	val body: Body? = null
) : Parcelable

@Parcelize
data class Body(

	@field:SerializedName("image")
	val image: String? = null
) : Parcelable
