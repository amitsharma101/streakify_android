package com.example.streakify.view.home.onboarding.otp

import android.content.Context
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.streakify.R
import com.example.streakify.base.BaseViewModel
import com.example.streakify.di.provider.ResourceProvider
import com.example.streakify.networkcall.NetworkResponse
import com.example.streakify.utils.UserConfig
import com.example.streakify.utils.extensions.ValidationUtil.Companion.isOTPValid
import com.example.streakify.utils.network.NetworkLiveData
import com.example.streakify.view.dialog.common.EventListener
import com.example.streakify.view.home.onboarding.data.Data
import com.example.streakify.view.home.onboarding.repo.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.streakify.BR
import com.example.streakify.networkcall.getError


class OtpVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(networkLiveData) {

    val phoneField = ObservableField("")
    val otpField = ObservableField("")
    val nameField = ObservableField("")
    val otpValid = ObservableBoolean(true)
    val isErrorShown = ObservableBoolean(false)
    var otpError = ObservableField(resourceProvider.getString(R.string.error_otp_empty))
    val signedInSuccess = ObservableBoolean()

    /* Listener to Detect otpField Changes */
    private var otpFieldOnPropertyChangedCallback: Observable.OnPropertyChangedCallback

    init {

        otpFieldOnPropertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (isOTPValid(otpField.get()!!)) {
                    otpValid.set(true)
                } else {
                    if (isErrorShown.get()) {
                        otpError.set("")
                    }
                    otpValid.set(false)
                }
            }
        }
        otpField.addOnPropertyChangedCallback(otpFieldOnPropertyChangedCallback)
    }

    fun validData(): Boolean {

        isErrorShown.set(true)
        if (!isOTPValid(otpField.get()!!)) {
            otpField.notifyPropertyChanged(BR.viewModel)
            if (otpField.get()!!.isEmpty()) {
                otpError.set(resourceProvider.getString(R.string.error_otp_empty))
            } else {
                otpError.set(resourceProvider.getString(R.string.please_enter_valid_otp))
            }
            otpValid.set(false)
        }
        return true

    }

    fun resendOtp() {
        eventListener.showLoading()
        viewModelScope.launch {
            val apiResponse = authRepository.checkLoginData("${phoneField.get()}")
            when (apiResponse) {
                is NetworkResponse.Success -> eventListener.dismissLoading()
                else -> {
                    eventListener.showMessageDialog(message = apiResponse.getError())
                    eventListener.dismissLoading()
                }
            }
        }
    }

    fun verifyOtp() {
        if (validData())

        /* Check Internet Connection */
            if (mNetworkErrorData.value?.status != true) {
                eventListener.let {
                    it.showMessageDialog(
                        message = resourceProvider.getString(R.string.check_internet),
                        positiveClickTitle = resourceProvider.getString(R.string.error_retry),
                        positiveClick = {
                            it.dismissMessageDialog()
                            verifyOtp()
                        },

                        )
                }
                return
            } else {
                viewModelScope.launch {
                    /* Show ProgressBar */
                    eventListener.showLoading()
                    /* Get API Response */
                    val apiResponse = authRepository.verifyOtp(
                        "${phoneField.get()}",
                        "${otpField.get()}",
                        "${nameField.get()}"
                    )
                    /* Perform Task at Background */
                    withContext(Dispatchers.IO) {
                        /* Notify Loading */
                        when (apiResponse) {

                            is NetworkResponse.Success -> {
//                                eventListener.dismissLoading()

                                /* Pass Data and Move To Dashboard Screen */
                                apiResponse.body?.data?.let { data ->
                                    /*save Auth Token*/
                                    data.token?.let {
                                        authRepository.setAuthToken(it)
                                    }
                                    setUserProps(data)
                                }
                            }

                            is NetworkResponse.UnknownError -> {
                                /* Show Message */
                                eventListener.dismissLoading()
                                eventListener.showMessageDialog(message = apiResponse.error.getError())
                            }

                            is NetworkResponse.ApiError -> {
                                /* Show Message */
                                eventListener.dismissLoading()
                                eventListener.showMessageDialog(message = apiResponse.getError())
                            }
                        }
                    }
                }
            }
    }

    private fun setUserProps(userData: Data) {
        UserConfig.setAuthToken(userData.token!!)
    }

    override fun onCleared() {
        super.onCleared()
        /* Remove Call Backs */
        otpField.removeOnPropertyChangedCallback(otpFieldOnPropertyChangedCallback)
    }
}