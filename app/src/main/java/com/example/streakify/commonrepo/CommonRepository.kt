package com.khatabook.billsngst.commonrepo

import com.example.streakify.application.App
import com.example.streakify.networkcall.ApiRequest
import com.example.streakify.utils.LocalPreferences
import okhttp3.RequestBody
import javax.inject.Inject

const val HEADER_PREFIX = "Token token="

class CommonRepository @Inject constructor(
    val app: App,
    val localPreferences: LocalPreferences,
    private val commonApiServices: CommonApiServices
) : ApiRequest() {

//    /** Add Party */
//    suspend fun addPartyData(
//        authtoken: String,
//        addPartyRequestDto: AddPartyRequestDto
//    ): NetworkResponse<PartyResponseDto> {
//        return apiRequest {
//            commonApiServices.addPartyData(
//                HEADER_PREFIX + authtoken,
//                addPartyRequestDto
//            )
//        }
//    }
}