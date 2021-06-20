package com.streakify.android.commonrepo

import com.streakify.android.view.home.profile.data.GetProfileResponse
import com.streakify.android.view.home.profile.data.UpdateProfileRequest
import com.streakify.android.view.home.profile.data.UpdateProfileResponse
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

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
}