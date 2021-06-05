package com.streakify.android.view.home.onboarding.login

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.streakify.android.R
import com.streakify.android.base.BaseViewModel
import com.streakify.android.base.Event
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.extensions.ValidationUtil.Companion.isPhoneValid
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.onboarding.repo.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.streakify.android.BR
import com.streakify.android.networkcall.getError


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

    fun checkNetwork(){
        if (mNetworkErrorData.value?.status != true) {
            eventListener.let {
                it.showMessageDialog(
                    message = resourceProvider.getString(R.string.check_internet),
                    positiveClickTitle = resourceProvider.getString(R.string.error_retry),
                    positiveClick = {
                        it.dismissMessageDialog()
                    },
                    )
            }
            return
        }
    }



    override fun onCleared() {
        super.onCleared()
        phoneField.removeOnPropertyChangedCallback(phoneFieldOnPropertyChangedCallback)
    }
}