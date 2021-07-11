package com.streakify.android.networkcall

import com.streakify.android.base.ErrorModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class ApiRequest {
    suspend inline fun <T : Any> apiRequest(
        crossinline call: suspend () -> Response<T>
    ): NetworkResponse<T> {
        return try {
            val response = withContext(Dispatchers.IO) {
                call.invoke()
            }
            withContext(Dispatchers.Main) {
                when (response.code()) {
                    /* Success */
                    in (200..299) -> NetworkResponse.Success(response.body())
                    else -> {
                        NetworkResponse.ApiError(
                            null,
                            Gson().fromJson(
                                response.errorBody()?.charStream(),
                                ErrorModel::class.java
                            ),
                            response.code()
                        )
                    }
                }
            }
        } catch (exception: Throwable) {
            withContext(Dispatchers.Main) {
                NetworkResponse.UnknownError(exception)
            }
        }
    }
}
