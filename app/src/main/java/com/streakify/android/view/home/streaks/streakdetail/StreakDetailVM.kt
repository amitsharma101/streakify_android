package com.streakify.android.view.home.streaks.streakdetail

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.streakify.android.application.AppConstants
import com.streakify.android.base.BaseViewModel
import com.streakify.android.commonrepo.CommonRepository
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.utils.extensions.Extensions.Companion.addPropertyChangedCallback
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
            if(get() == AppConstants.STREAK_TYPE_INDEFINITE)progressVisibility.set(View.GONE)
            else progressVisibility.set(View.VISIBLE)
        }
    }

    val progressVisibility = ObservableInt(View.GONE)
    val punchInVisibility = ObservableInt(View.GONE)
    val punchOutVisibility = ObservableInt(View.GONE)

    val editButtonVisibility = ObservableInt(View.GONE)

    init {

    }

    fun onAttach(streakId:Int){
        viewModelScope.launch {
            val userId = localPreferences.readValue(LocalPreferences.USER_ID).asLiveData().value
            localPreferences.readValue(LocalPreferences.AUTH_TOKEN).collect {token->
                eventListener.showLoading()
                val apiResponse = commonRepository.streakDetail(token!!,streakId.toString())
                when(apiResponse){
                    is NetworkResponse.Success -> {
                        eventListener.dismissLoading()

                        val res = apiResponse.body?.body
                        streakName.set(res?.name)
                        if(res?.type == AppConstants.STREAK_TYPE_INDEFINITE){
                            progressVisibility.set(View.GONE)
                        }
                        else{
                            progressVisibility.set(View.VISIBLE)
                        }
                        val meAsPar = res?.participants?.find { it?.userId == userId }
                        if(meAsPar?.punchIn == true){
                            punchInVisibility.set(View.GONE)
                            punchOutVisibility.set(View.VISIBLE)
                        }
                        else{
                            punchInVisibility.set(View.VISIBLE)
                            punchOutVisibility.set(View.GONE)
                        }
                    }
                    else -> {
                        eventListener.dismissLoading()
                    }
                }
            }
        }
    }


}