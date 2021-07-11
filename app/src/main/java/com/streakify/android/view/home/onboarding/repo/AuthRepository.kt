package com.streakify.android.view.home.onboarding.repo

import androidx.annotation.VisibleForTesting
import com.streakify.android.application.App
import com.streakify.android.base.BaseModel
import com.streakify.android.commonrepo.HEADER_PREFIX
import com.streakify.android.networkcall.ApiRequest
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.view.home.onboarding.data.GetTokenRequest
import com.streakify.android.view.home.onboarding.data.GetTokenResponse
import com.streakify.android.view.home.onboarding.data.LoginModel
import com.streakify.android.view.home.profile.data.GetProfileResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    val app: App,
    val localPreferences: LocalPreferences,
    private val authApiServices: AuthApiServices
) : ApiRequest() {

    suspend fun getProfile(): NetworkResponse<GetProfileResponse> {
        return apiRequest {
            authApiServices.getProfile()
        }
    }

    /** Check Login Data */
    suspend fun getToken(getTokenRequest: GetTokenRequest): NetworkResponse<GetTokenResponse> {
        return apiRequest { authApiServices.getToken(getTokenRequest) }
    }

    /** Read Auth Token from DataStore */
    fun getAuthToken(): Flow<String?> =
        localPreferences.readValue(LocalPreferences.AUTH_TOKEN)

    fun getUserId(): Flow<Int?> =
        localPreferences.readValue(LocalPreferences.USER_ID)

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

    suspend fun setUserId(data: Int) {
        return localPreferences.storeValue(LocalPreferences.USER_ID, data)
    }

    @VisibleForTesting
    /** reset Auth Token in DataStore */
    suspend fun resetAuthToken() {
        return localPreferences.resetValue(LocalPreferences.AUTH_TOKEN, "")
    }

    suspend fun logout(){
        localPreferences.resetValue(LocalPreferences.AUTH_TOKEN, "")
        localPreferences.resetValue(LocalPreferences.REFRESH_TOKEN, "")
        localPreferences.resetValue(LocalPreferences.FIREBASE_TOKEN, "")
        localPreferences.resetValue(LocalPreferences.USER_ID, -1)
    }
}