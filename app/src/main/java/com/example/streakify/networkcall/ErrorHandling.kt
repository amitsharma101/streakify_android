package com.example.streakify.networkcall

import com.example.streakify.base.BaseModel
import com.example.streakify.utils.extensions.ValidationUtil
import com.google.gson.JsonSyntaxException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLException

/**
 * Handle Different Types of Exceptions
 * */

object ExceptionMessage {

    const val SHOW_RESPONSE_CODE: Boolean = true
    const val SHOW_ERROR_CAUSE: Boolean = true

    const val INTERNET_CONNECTION: String = "Please Check Your Internet Connection"
    const val SSL: String = "SSL Exception"
    const val REQUEST_TIMEOUT: String = "Request Time"
    const val INVALID_RESPONSE: String = "Invalid Server Response"
    const val SOMETHING_WENT_WRONG: String = "Something went wrong"

}

fun Throwable.getError() = ErrorHandling().getThrowableMessage(this)

fun NetworkResponse<BaseModel>.getError(): String =
    when (this) {
        is NetworkResponse.ApiError -> {
            var errorMessage = error.message?.capitalize(ValidationUtil.locale)
            error.data?.values?.forEach {
                errorMessage = "$errorMessage \n$it"
            }
            errorMessage ?: ExceptionMessage.SOMETHING_WENT_WRONG
        }
        is NetworkResponse.UnknownError -> error.getError()
        else -> ExceptionMessage.SOMETHING_WENT_WRONG
    }

fun Int.isAuthError(): Boolean = this == 401 || this == 403

class ErrorHandling {

    fun getThrowableMessage(exception: Throwable): String {

        return when (exception) {

            is SSLException ->
                "${ExceptionMessage.SSL}${getCauseMessage(exception)}"

            is SocketTimeoutException -> ExceptionMessage.SOMETHING_WENT_WRONG

            is IOException ->
                "${ExceptionMessage.INTERNET_CONNECTION}${getCauseMessage(exception)}"

            is JsonSyntaxException ->
                "${ExceptionMessage.INVALID_RESPONSE}${getCauseMessage(exception)}"

            else ->
                "${ExceptionMessage.SOMETHING_WENT_WRONG}${getCauseMessage(exception)}"
        }
    }

    private fun getCauseMessage(exception: Throwable): String =
        if (ExceptionMessage.SHOW_ERROR_CAUSE) {
            ": ${exception.javaClass} - ${exception.message}"
        } else ""
}