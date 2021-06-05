package com.example.streakify.networkcall

import androidx.annotation.Keep
import com.example.streakify.base.ErrorModel

//TODO REMOVE used resources
sealed class NetworkResponse<out T : Any> {

    /**
     * Success Response with Body
     */
    @Keep
    data class Success<out T : Any>(val body: T?) : NetworkResponse<T>()

    /**
     * Failure Response with Error Body
     */
    @Keep
    data class ApiError<out U : Any>(val body: U? = null, val error: ErrorModel, val code: Int) : NetworkResponse<U>()

    /**
     * Auth Error Response with Error Body
     */
//    @Keep
//    data class AuthError<out U : Any>(val body: U?, val code: Int) : NetworkResponse<Nothing>()

    /**
     * Network Error
     */
    //@Keep
    //data class NetworkError(val error: IOException) : NetworkResponse<Nothing>()

    /**
     * Time-out Error
     */
    //@Keep
    //data class TimeOut(val error: SocketTimeoutException) : NetworkResponse<Nothing>()

    /**
     * JSON Parsing Error
     */
    //@Keep
    //data class JsonSyntaxError(val error: Throwable) : NetworkResponse<Nothing>()

    /**
     * For example, json parsing error
     */
    @Keep
    data class UnknownError(val error: Throwable) : NetworkResponse<Nothing>()
}