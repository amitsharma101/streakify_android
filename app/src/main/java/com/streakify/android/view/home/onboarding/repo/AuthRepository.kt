package com.streakify.android.view.home.onboarding.repo

import androidx.annotation.VisibleForTesting
import com.streakify.android.application.App
import com.streakify.android.base.BaseModel
import com.streakify.android.networkcall.ApiRequest
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.view.home.onboarding.data.LoginModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    val app: App,
    val localPreferences: LocalPreferences,
    private val authApiServices: AuthApiServices
) : ApiRequest() {

    /** Check Login Data */
    suspend fun checkLoginData(phone_number: String): NetworkResponse<BaseModel> {
        return apiRequest { authApiServices.checkLoginData(phone_number) }
    }

    /** Check OTP Data */
    suspend fun verifyOtp(
        phone_number: String,
        otp: String,
        name: String
    ): NetworkResponse<LoginModel> {
        return apiRequest { authApiServices.checkVerifyOtpData(
            phone_number,
            otp,
            name) }
    }

    /** Read Auth Token from DataStore */
    fun getAuthToken(): Flow<String?> =
        localPreferences.readValue(LocalPreferences.AUTH_TOKEN)

    /** Store Auth Token in DataStore */
    suspend fun setAuthToken(data: String) {
        return localPreferences.storeValue(LocalPreferences.AUTH_TOKEN, data)
    }

    @VisibleForTesting
    /** reset Auth Token in DataStore */
    suspend fun resetAuthToken() {
        return localPreferences.resetValue(LocalPreferences.AUTH_TOKEN, "")
    }
}