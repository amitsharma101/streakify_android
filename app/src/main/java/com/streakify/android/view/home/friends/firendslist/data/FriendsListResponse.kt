package com.streakify.android.view.home.friends.firendslist.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FriendsListResponse(

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("body")
	val body: FriendsList? = null
) : Parcelable

@Parcelize
data class FriendsList(

	@field:SerializedName("pending_friends")
	val pendingFriends: List<PendingFriendsItem?>? = null,

	@field:SerializedName("active_friends")
	val activeFriends: List<ActiveFriendsItem?>? = null
) : Parcelable

@Parcelize
data class ActiveFriendsItem(

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("profile_pic")
	val profilePic: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("mobile_number")
	val mobileNumber: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable

@Parcelize
data class PendingFriendsItem(

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("profile_pic")
	val profilePic: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("mobile_number")
	val mobileNumber: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
