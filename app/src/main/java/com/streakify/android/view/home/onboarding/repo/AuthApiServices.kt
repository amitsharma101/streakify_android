package com.streakify.android.view.home.onboarding.repo

import com.streakify.android.base.BaseModel
import com.streakify.android.networkcall.SuccessResponse
import com.streakify.android.view.home.launcher.UpdateCheckerRequest
import com.streakify.android.view.home.launcher.UpdateCheckerResponse
import com.streakify.android.view.home.onboarding.data.GetTokenRequest
import com.streakify.android.view.home.onboarding.data.GetTokenResponse
import com.streakify.android.view.home.onboarding.data.LoginModel
import com.streakify.android.view.home.profile.data.GetProfileResponse
import com.streakify.android.view.home.profile.data.SendFcmTokenRequest
import com.streakify.android.view.home.profile.data.UpdateProfileRequest
import com.streakify.android.view.home.profile.data.UpdateProfileResponse
import retrofit2.Response
import retrofit2.http.*


interface AuthApiServices {

    /** Check Token*/
    @POST("api/v1/auth/get-token")
    suspend fun getToken(@Body getTokenRequest: GetTokenRequest): Response<GetTokenResponse>

    @GET("api/v1/users/profile")
    suspend fun getProfile(): Response<GetProfileResponse>

    @POST("api/v1/core/update-checker")
    suspend fun getToken(@Body checkerRequest: UpdateCheckerRequest): Response<UpdateCheckerResponse>

    @PATCH("api/v1/users/profile")
    suspend fun sendFcmToken(
        @Body sendFcmTokenRequest: SendFcmTokenRequest
    ): Response<SuccessResponse>
}