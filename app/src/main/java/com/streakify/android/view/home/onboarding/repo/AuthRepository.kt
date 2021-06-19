package com.streakify.android.view.home.onboarding.repo

import androidx.annotation.VisibleForTesting
import com.streakify.android.application.App
import com.streakify.android.base.BaseModel
import com.streakify.android.networkcall.ApiRequest
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.view.home.onboarding.data.GetTokenRequest
import com.streakify.android.view.home.onboarding.data.GetTokenResponse
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
    suspend fun getToken(getTokenRequest: GetTokenRequest): NetworkResponse<GetTokenResponse> {
        return apiRequest { authApiServices.getToken(getTokenRequest) }
    }

    /** Read Auth Token from DataStore */
    fun getAuthToken(): Flow<String?> =
        localPreferences.readValue(LocalPreferences.AUTH_TOKEN)

    /** Store Auth Token in DataStore */
    suspend fun setAuthToken(data: String) {
        return localPreferences.storeValue(LocalPreferences.AUTH_TOKEN, data)
    }

    /** Store Auth Token in DataStore */
    suspend fun setRefreshToken(data: String) {
        return localPreferences.storeValue(LocalPreferences.REFRESH_TOKEN, data)
    }

    /** Store Auth Token in DataStore */
    suspend fun setFirebaseToken(data: String) {
        return localPreferences.storeValue(LocalPreferences.FIREBASE_TOKEN, data)
    }

    @VisibleForTesting
    /** reset Auth Token in DataStore */
    suspend fun resetAuthToken() {
        return localPreferences.resetValue(LocalPreferences.AUTH_TOKEN, "")
    }
}