package com.streakify.android.networkcall

import com.streakify.android.BuildConfig
import com.streakify.android.commonrepo.HEADER_PREFIX
import com.streakify.android.utils.LocalPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response

const val AUTHORIZATION = "Authorization"
class NetworkInterceptor(localPreferences: LocalPreferences) : Interceptor {

    var authToken: String? = null

    init {
        CoroutineScope(Dispatchers.IO).launch {
            localPreferences.readValue(LocalPreferences.AUTH_TOKEN)
                .collect { token ->
                    authToken = token
                }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        if(authToken.isNullOrBlank()){
            proceed(
                request()
                    .newBuilder()
//                .addHeader("X-Os-Name", getOSName())
//                .addHeader("X-App-Version", getVersionName())
//                .addHeader("X-App-Version-Code", getVersionCode())
//                    .addHeader(AUTHORIZATION, "$HEADER_PREFIX${authToken}")
                    .build()
            )
        }
        else{
            proceed(
                request()
                    .newBuilder()
//                .addHeader("X-Os-Name", getOSName())
//                .addHeader("X-App-Version", getVersionName())
//                .addHeader("X-App-Version-Code", getVersionCode())
                    .addHeader(AUTHORIZATION, "$HEADER_PREFIX${authToken}")
                    .build()
            )
        }
    }

    private fun getOSName(): String {
        return "Android"
    }

    private fun getVersionName(): String {
        return BuildConfig.VERSION_NAME
    }

    private fun getVersionCode(): String {
        return BuildConfig.VERSION_CODE.toString()
    }
}