package com.streakify.android.view.home.onboarding.repo

import com.streakify.android.base.BaseModel
import com.streakify.android.view.home.onboarding.data.GetTokenRequest
import com.streakify.android.view.home.onboarding.data.GetTokenResponse
import com.streakify.android.view.home.onboarding.data.LoginModel
import com.streakify.android.view.home.profile.data.GetProfileResponse
import retrofit2.Response
import retrofit2.http.*


interface AuthApiServices {

    /** Check Token*/
    @POST("api/v1/auth/get-token")
    suspend fun getToken(@Body getTokenRequest: GetTokenRequest): Response<GetTokenResponse>

    @GET("api/v1/users/profile")
    suspend fun getProfile(
        @Header("Authorization") authToken: String
    ): Response<GetProfileResponse>
}