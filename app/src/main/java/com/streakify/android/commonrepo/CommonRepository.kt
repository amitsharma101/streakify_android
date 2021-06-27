package com.streakify.android.commonrepo

import com.streakify.android.application.App
import com.streakify.android.networkcall.ApiRequest
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.LocalPreferences
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
import com.streakify.android.view.home.streaks.streakdetail.data.StreakDetailResponse
import com.streakify.android.view.home.streaks.streaklist.data.PunchResponse
import com.streakify.android.view.home.streaks.streaklist.data.StreakListResponse
import javax.inject.Inject

const val HEADER_PREFIX = "Bearer "

class CommonRepository @Inject constructor(
    val app: App,
    val localPreferences: LocalPreferences,
    private val commonApiServices: CommonApiServices
) : ApiRequest() {

    suspend fun getProfile(
        authtoken: String,
    ): NetworkResponse<GetProfileResponse> {
        return apiRequest {
            commonApiServices.getProfile(
                HEADER_PREFIX + authtoken
            )
        }
    }

    suspend fun updateProfile(
        authtoken: String,
        updateProfileRequest: UpdateProfileRequest
    ): NetworkResponse<UpdateProfileResponse> {
        return apiRequest {
            commonApiServices.updateProfile(
                HEADER_PREFIX + authtoken,
                updateProfileRequest
            )
        }
    }

    suspend fun getFriends(
        authtoken: String
    ): NetworkResponse<FriendsListResponse> {
        return apiRequest {
            commonApiServices.getFriends(
                HEADER_PREFIX + authtoken
            )
        }
    }

    suspend fun addFriend(
        authtoken: String,
        addFriendRequest: AddFriendRequest
    ): NetworkResponse<AddFriendResponse> {
        return apiRequest {
            commonApiServices.addFriend(
                HEADER_PREFIX + authtoken,
                addFriendRequest
            )
        }
    }

    suspend fun actionFriendRequest(
        authtoken: String,
        friendRequestActionRequest: FriendRequestActionRequest,
        id : String
    ): NetworkResponse<FriendRequestActionResponse> {
        return apiRequest {
            commonApiServices.actionFriendRequest(
                HEADER_PREFIX + authtoken,
                friendRequestActionRequest,
                id
            )
        }
    }

    suspend fun getStreaks(
        authtoken: String
    ): NetworkResponse<StreakListResponse> {
        return apiRequest {
            commonApiServices.getStreaks(
                HEADER_PREFIX + authtoken
            )
        }
    }

    suspend fun punch(
        authtoken: String,
        id : String
    ): NetworkResponse<PunchResponse> {
        return apiRequest {
            commonApiServices.punch(
                HEADER_PREFIX + authtoken,
                id
            )
        }
    }

    suspend fun createStreak(
        authtoken: String,
        createStrealRequest : CreateStreakRequest
    ): NetworkResponse<CreateStreakResponse> {
        return apiRequest {
            commonApiServices.createStreak(
                HEADER_PREFIX + authtoken,
                createStrealRequest
            )
        }
    }

    suspend fun streakDetail(
        authtoken: String,
        id : String
    ): NetworkResponse<StreakDetailResponse> {
        return apiRequest {
            commonApiServices.streakDetail(
                HEADER_PREFIX + authtoken,
                id
            )
        }
    }
}