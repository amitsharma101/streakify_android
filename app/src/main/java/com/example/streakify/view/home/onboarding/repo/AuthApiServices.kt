package com.example.streakify.view.home.onboarding.repo

import com.example.streakify.base.BaseModel
import com.example.streakify.view.home.onboarding.data.LoginModel
import retrofit2.Response
import retrofit2.http.*


interface AuthApiServices {

    /** Check Login Data */
    @FormUrlEncoded
    @POST("get_otp")
    suspend fun checkLoginData(@Field("phone_number") phone_number: String): Response<BaseModel>


    /** Check Verify Otp Data */
    @POST("login")
    suspend fun checkVerifyOtpData(@Query("phone_number") phone_number: String,
                                   @Query("otp") otp: String,
                                   @Query("name") name: String): Response<LoginModel>
}