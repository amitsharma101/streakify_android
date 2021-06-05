package com.khatabook.billsngst.commonrepo

import com.streakify.android.application.App
import com.streakify.android.networkcall.ApiRequest
import com.streakify.android.utils.LocalPreferences
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