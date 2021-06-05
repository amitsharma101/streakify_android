package com.example.streakify.view.home.onboarding.login

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.streakify.R
import com.example.streakify.base.BaseViewModel
import com.example.streakify.base.Event
import com.example.streakify.di.provider.ResourceProvider
import com.example.streakify.networkcall.NetworkResponse
import com.example.streakify.utils.extensions.ValidationUtil.Companion.isPhoneValid
import com.example.streakify.utils.network.NetworkLiveData
import com.example.streakify.view.dialog.common.EventListener
import com.example.streakify.view.home.onboarding.repo.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.streakify.BR
import com.example.streakify.networkcall.getError


class LoginVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(networkLiveData) {

    val phoneValid = ObservableBoolean(true)
    val phoneField = ObservableField("")
    var phoneError =
        ObservableField(resourceProvider.getString(R.string.error_phone_empty))
    val isErrorShown = ObservableBoolean(false)
    private var phoneFieldOnPropertyChangedCallback: Observable.OnPropertyChangedCallback
    val countryCodeField = ObservableField<Int>()

    init {

        phoneFieldOnPropertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {

                if (isPhoneValid(phoneField.get()!!)) {
                    phoneValid.set(true)
                } else {
                    if (isErrorShown.get()) {
                        phoneError.set("")
                    }
                    phoneValid.set(false)
                }
            }
        }
        phoneField.addOnPropertyChangedCallback(phoneFieldOnPropertyChangedCallback)

    }

    fun validData(): Boolean {

        isErrorShown.set(true)
        if (!isPhoneValid(phoneField.get()!!)) {
            phoneField.notifyPropertyChanged(BR.viewModel)
            if (phoneField.get()!!.isEmpty()) {
                phoneError.set(resourceProvider.getString(R.string.error_phone_empty))
            } else {
                phoneError.set(resourceProvider.getString(R.string.please_enter_valid_number))
            }
            phoneValid.set(false)
            return false
        }
        return true
    }

    fun doLogin() {

        if (validData())

            /* Check Internet Connection */
            if (mNetworkErrorData.value?.status != true) {
                eventListener.let {
                    it.showMessageDialog(
                        message = resourceProvider.getString(R.string.check_internet),
                        positiveClickTitle = resourceProvider.getString(R.string.error_retry),
                        positiveClick = {
                            it.dismissMessageDialog()
                            doLogin()
                        },

                        )
                }
                return
            } else {

                viewModelScope.launch {

                    /* Show ProgressBar */
                    eventListener.showLoading()

                    /* Get API Response */
                    val apiResponse = authRepository.checkLoginData("${phoneField.get()}")

                    /* Perform Task at Background */
                    withContext(Dispatchers.IO) {

                        /* Notify Loading */
                        eventListener.dismissLoading()

                        when (apiResponse) {

                            is NetworkResponse.Success -> {

                                /* Pass Data and Move To OTP Screen */
                                mDestinationForward
                                    .postValue(Event("${phoneField.get()}"))
                            }

                            is NetworkResponse.UnknownError -> {

                                /* Show Message */
                                eventListener.showMessageDialog(message = apiResponse.error.getError())
                            }

                            is NetworkResponse.ApiError -> {

                                /* Show Message */
                                eventListener.showMessageDialog(message = apiResponse.getError())
                            }
                        }

                    }


                }
            }
    }


    override fun onCleared() {
        super.onCleared()
        phoneField.removeOnPropertyChangedCallback(phoneFieldOnPropertyChangedCallback)
    }
}