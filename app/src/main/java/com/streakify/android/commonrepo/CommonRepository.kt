package com.streakify.android.commonrepo

import com.streakify.android.application.App
import com.streakify.android.networkcall.ApiRequest
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.networkcall.SuccessResponse
import com.streakify.android.utils.LocalPreferences
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
import javax.inject.Inject

const val HEADER_PREFIX = "Bearer "

class CommonRepository @Inject constructor(
    val app: App,
    val localPreferences: LocalPreferences,
    private val commonApiServices: CommonApiServices
) : ApiRequest() {

    suspend fun getProfile(): NetworkResponse<GetProfileResponse> {
        return apiRequest {
            commonApiServices.getProfile()
        }
    }

    suspend fun updateProfile(
        updateProfileRequest: UpdateProfileRequest
    ): NetworkResponse<UpdateProfileResponse> {
        return apiRequest {
            commonApiServices.updateProfile(
                updateProfileRequest
            )
        }
    }

    suspend fun getFriends(): NetworkResponse<FriendsListResponse> {
        return apiRequest {
            commonApiServices.getFriends()
        }
    }

    suspend fun addFriend(
        addFriendRequest: AddFriendRequest
    ): NetworkResponse<AddFriendResponse> {
        return apiRequest {
            commonApiServices.addFriend(
                addFriendRequest
            )
        }
    }

    suspend fun actionFriendRequest(
        friendRequestActionRequest: FriendRequestActionRequest,
        id : String
    ): NetworkResponse<FriendRequestActionResponse> {
        return apiRequest {
            commonApiServices.actionFriendRequest(
                friendRequestActionRequest,
                id
            )
        }
    }

    suspend fun getStreaks(): NetworkResponse<StreakListResponse> {
        return apiRequest {
            commonApiServices.getStreaks()
        }
    }

    suspend fun punch(
        punchInRequest: PunchInRequest,
        id : String
    ): NetworkResponse<PunchResponse> {
        return apiRequest {
            commonApiServices.punch(
                punchInRequest,
                id
            )
        }
    }

    suspend fun createStreak(
        createStrealRequest : CreateStreakRequest
    ): NetworkResponse<CreateStreakResponse> {
        return apiRequest {
            commonApiServices.createStreak(
                createStrealRequest
            )
        }
    }

    suspend fun streakDetail(
        id : String
    ): NetworkResponse<StreakDetailResponse> {
        return apiRequest {
            commonApiServices.streakDetail(
                id
            )
        }
    }

    suspend fun updateStreak(
        updateStreakRequest: UpdateStreakRequest,
        id : String
    ): NetworkResponse<SuccessResponse> {
        return apiRequest {
            commonApiServices.updateStreak(
                updateStreakRequest,
                id
            )
        }
    }

    suspend fun pushImagesData(
        pushDto: RequestBody
    ): NetworkResponse<UploadImageResponse> {
        return apiRequest {
            commonApiServices.pushData(
                pushDto
            )
        }
    }

    suspend fun deleteStreak(
        id: String
    ): NetworkResponse<SuccessResponse> {
        return apiRequest {
            commonApiServices.deleteStreak(
                id
            )
        }
    }
}