package com.streakify.android.view.home.streaks.streakdetail

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.streakify.android.R
import com.streakify.android.application.AppConstants
import com.streakify.android.base.BaseViewModel
import com.streakify.android.commonrepo.CommonRepository
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.Calculator
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.utils.Util
import com.streakify.android.utils.extensions.Extensions.Companion.addPropertyChangedCallback
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.streaks.StreaksEvent
import com.streakify.android.view.home.streaks.streakdetail.data.Body
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class StreakDetailVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val commonRepository: CommonRepository,
    val localPreferences: LocalPreferences,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(networkLiveData) {

    val streakName = ObservableField("")
    val streakType = ObservableInt(AppConstants.STREAK_TYPE_INDEFINITE).apply {
        addPropertyChangedCallback {
            if (get() == AppConstants.STREAK_TYPE_INDEFINITE) progressVisibility.set(View.GONE)
            else progressVisibility.set(View.VISIBLE)
        }
    }

    val progressVisibility = ObservableInt(View.GONE)
    val punchInVisibility = ObservableInt(View.GONE)
    val punchOutVisibility = ObservableInt(View.GONE)

    val editButtonVisibility = ObservableInt(View.GONE)

    var streakDetails: Body? = null

    init {

    }

    fun onAttach(streakId: Int) {
        viewModelScope.launch {
                localPreferences.readValue(LocalPreferences.USER_ID).collect { userId ->
                    eventListener.showLoading()
                    val apiResponse = commonRepository.streakDetail(streakId.toString())
                    when (apiResponse) {
                        is NetworkResponse.Success -> {
                            eventListener.dismissLoading()

                            val res = apiResponse.body?.body
                            streakDetails = res

                            streakName.set(res?.name)

                            if (res?.createdBy == userId) {
                                editButtonVisibility.set(View.VISIBLE)
                            } else {
                                editButtonVisibility.set(View.GONE)
                            }

                            if (res?.type == AppConstants.STREAK_TYPE_INDEFINITE) {
                                progressVisibility.set(View.GONE)
                            } else {
                                progressVisibility.set(View.VISIBLE)
                            }
                            val meAsPar = res?.participants?.find { it?.userId == userId }
                            if (meAsPar?.punchIn == true) {
                                punchInVisibility.set(View.GONE)
                                punchOutVisibility.set(View.VISIBLE)
                            } else {
                                punchInVisibility.set(View.VISIBLE)
                                punchOutVisibility.set(View.GONE)
                            }


                            val myStreakData = Calculator.getStreakData(meAsPar?.startDate,streakDetails?.type!!,streakDetails?.maxDuration?:0,meAsPar?.punchIn!!)
                            val days = myStreakData.daysFinished
                            val streakPercentage : Float = myStreakData.percentage

                            val ds = when(streakDetails?.type){
                                AppConstants.STREAK_TYPE_DEFINITE -> {
                                    resourceProvider.getString(R.string.x_of_ydays_streak,days.toString(),
                                        streakDetails!!.maxDuration.toString())
                                }
                                AppConstants.STREAK_TYPE_INDEFINITE -> {
                                    resourceProvider.getString(R.string.x_days_streak,days.toString())
                                }
                                else -> {
                                    ""
                                }
                            }
                            displayString.set(ds)
                            event.value = StreaksEvent.DefinitePercentage(streakPercentage)





                            val participants = res?.participants?.filter { it?.userId != userId }
                            event.value = StreaksEvent.StreakDetailParticipantsFetched(participants)
                        }
                        is NetworkResponse.ApiError -> {
                            eventListener.dismissLoading()
                            eventListener.showMessageDialog(apiResponse.error?.detail,
                                "Oops",
                                positiveClick = {
                                    eventListener.dismissMessageDialog()
                                })
                        }
                        else -> {
                            eventListener.dismissLoading()
                        }
                    }
                }
        }
    }


    val sdf = SimpleDateFormat(AppConstants.APP_DATE_FORMAT)



    val displayString = ObservableField("")


}