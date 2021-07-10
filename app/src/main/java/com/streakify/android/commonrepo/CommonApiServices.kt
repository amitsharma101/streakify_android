package com.streakify.android.commonrepo

import com.streakify.android.networkcall.SuccessResponse
import com.streakify.android.view.home.friends.addfriend.data.AddFriendRequest
import com.streakify.android.view.home.friends.addfriend.data.AddFriendResponse
import com.streakify.android.view.home.friends.firendslist.data.FriendRequestActionRequest
import com.streakify.android.view.home.friends.firendslist.data.FriendRequestActionResponse
import com.streakify.android.view.home.friends.firendslist.data.FriendsListResponse
import com.streakify.android.view.home.image.UploadImageResponse
import com.streakify.android.view.home.profile.data.GetProfileResponse
import com.streakify.android.view.home.profile.data.UpdateProfileRequest
import com.streakify.android.view.home.profile.data.UpdateProfileResponse
import com.streakify.android.view.home.streaks.editstreak.data.CreateStreakRequest
import com.streakify.android.view.home.streaks.editstreak.data.CreateStreakResponse
import com.streakify.android.view.home.streaks.editstreak.data.UpdateStreakRequest
import com.streakify.android.view.home.streaks.streakdetail.data.StreakDetailResponse
import com.streakify.android.view.home.streaks.streaklist.data.PunchInRequest
import com.streakify.android.view.home.streaks.streaklist.data.PunchResponse
import com.streakify.android.view.home.streaks.streaklist.data.StreakListResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

@JvmSuppressWildcards
interface CommonApiServices {

    @Multipart
    @POST("api/v1/core/upload-image")
    suspend fun pushData(
        @Part("image\"; filename=\"file.jpg") file: RequestBody
    ): Response<UploadImageResponse>

    @GET("api/v1/users/profile")
    suspend fun getProfile(): Response<GetProfileResponse>

    @PATCH("api/v1/users/profile")
    suspend fun updateProfile(
        @Body updateProfileRequets: UpdateProfileRequest
    ): Response<UpdateProfileResponse>

    @GET("api/v1/friends/friends")
    suspend fun getFriends(): Response<FriendsListResponse>

    @POST("api/v1/friends/friends")
    suspend fun addFriend(
        @Body addFriendRequest : AddFriendRequest
    ): Response<AddFriendResponse>

    @PATCH("api/v1/friends/update-request-status/{id}")
    suspend fun actionFriendRequest(
        @Body actionFriendRequest : FriendRequestActionRequest,
        @Path("id") id : String
    ): Response<FriendRequestActionResponse>

    @GET("api/v1/streaks/streaks")
    suspend fun getStreaks(): Response<StreakListResponse>

    @PATCH("api/v1/streaks/punch/{id}")
    suspend fun punch(
        @Body punchInRequest: PunchInRequest,
        @Path("id") id : String
    ): Response<PunchResponse>

    @POST("api/v1/streaks/streaks")
    suspend fun createStreak(
        @Body createStreakRequest: CreateStreakRequest
    ): Response<CreateStreakResponse>

    @GET("api/v1/streaks/streaks/{id}")
    suspend fun streakDetail(
        @Path("id") id : String
    ): Response<StreakDetailResponse>

    @PATCH("api/v1/streaks/streaks/{id}")
    suspend fun updateStreak(
        @Body updateStreakRequest: UpdateStreakRequest,
        @Path("id") id : String
    ): Response<SuccessResponse>

}