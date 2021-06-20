package com.streakify.android.commonrepo

import com.streakify.android.application.App
import com.streakify.android.networkcall.ApiRequest
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.view.home.profile.data.GetProfileResponse
import com.streakify.android.view.home.profile.data.UpdateProfileRequest
import com.streakify.android.view.home.profile.data.UpdateProfileResponse
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
}