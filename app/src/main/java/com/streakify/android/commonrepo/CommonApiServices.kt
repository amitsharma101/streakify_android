package com.streakify.android.commonrepo

import com.streakify.android.networkcall.SuccessResponse
import com.streakify.android.view.home.friends.addfriend.data.AddFriendRequest
import com.streakify.android.view.home.friends.addfriend.data.AddFriendResponse
import com.streakify.android.view.home.friends.firendslist.data.FriendRequestActionRequest
import com.streakify.android.view.home.friends.firendslist.data.FriendRequestActionResponse
import com.streakify.android.view.home.friends.firendslist.data.FriendsListResponse
import com.streakify.android.view.home.profile.data.GetProfileResponse
import com.streakify.android.view.home.profile.data.UpdateProfileRequest
import com.streakify.android.view.home.profile.data.UpdateProfileResponse
import com.streakify.android.view.home.streaks.editstreak.data.CreateStreakRequest
import com.streakify.android.view.home.streaks.editstreak.data.CreateStreakResponse
import com.streakify.android.view.home.streaks.editstreak.data.UpdateStreakRequest
import com.streakify.android.view.home.streaks.streakdetail.data.StreakDetailResponse
import com.streakify.android.view.home.streaks.streaklist.data.PunchResponse
import com.streakify.android.view.home.streaks.streaklist.data.StreakListResponse
import retrofit2.Response
import retrofit2.http.*

@JvmSuppressWildcards
interface CommonApiServices {

    @GET("api/v1/users/profile")
    suspend fun getProfile(
        @Header("Authorization") authToken: String
    ): Response<GetProfileResponse>

    @PATCH("api/v1/users/profile")
    suspend fun updateProfile(
        @Header("Authorization") authToken: String,
        @Body updateProfileRequets: UpdateProfileRequest
    ): Response<UpdateProfileResponse>

    @GET("api/v1/friends/friends")
    suspend fun getFriends(
        @Header("Authorization") authToken: String
    ): Response<FriendsListResponse>

    @POST("api/v1/friends/friends")
    suspend fun addFriend(
        @Header("Authorization") authToken: String,
        @Body addFriendRequest : AddFriendRequest
    ): Response<AddFriendResponse>

    @PATCH("api/v1/friends/update-request-status/{id}")
    suspend fun actionFriendRequest(
        @Header("Authorization") authToken: String,
        @Body actionFriendRequest : FriendRequestActionRequest,
        @Path("id") id : String
    ): Response<FriendRequestActionResponse>

    @GET("api/v1/streaks/streaks")
    suspend fun getStreaks(
        @Header("Authorization") authToken: String
    ): Response<StreakListResponse>

    @PATCH("api/v1/streaks/punch-in/{id}")
    suspend fun punch(
        @Header("Authorization") authToken: String,
        @Path("id") id : String
    ): Response<PunchResponse>

    @POST("api/v1/streaks/streaks")
    suspend fun createStreak(
        @Header("Authorization") authToken: String,
        @Body createStreakRequest: CreateStreakRequest
    ): Response<CreateStreakResponse>

    @GET("api/v1/streaks/streaks/{id}")
    suspend fun streakDetail(
        @Header("Authorization") authToken: String,
        @Path("id") id : String
    ): Response<StreakDetailResponse>

    @PATCH("api/v1/streaks/streaks/{id}")
    suspend fun updateStreak(
        @Header("Authorization") authToken: String,
        @Body updateStreakRequest: UpdateStreakRequest,
        @Path("id") id : String
    ): Response<SuccessResponse>
}