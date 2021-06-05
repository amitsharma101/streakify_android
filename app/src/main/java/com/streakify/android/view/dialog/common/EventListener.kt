package com.streakify.android.view.dialog.common

import android.view.Gravity
import androidx.lifecycle.MutableLiveData
import com.streakify.android.R
import com.streakify.android.base.BaseModel
import com.streakify.android.base.Event
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.view.dialog.DialogViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventListener @Inject constructor(
    private val resourceProvider: ResourceProvider
) {
    var snackbarMessage = MutableLiveData<Event<String>>()
    val isDialogVisible = MutableLiveData<Event<Boolean>>()
    val closeApp = MutableLiveData<Event<Boolean>>()
    val loadingDialogConfig = MutableLiveData<Event<LoadingUiConfig>>()
    val errorDialogConfig = MutableLiveData<Event<DialogUiConfig>>()
    var errorDialogViewModel = MutableLiveData<Event<DialogViewModel>>()
    val closeActivity = MutableLiveData<Event<Boolean>>()
    val updateConfiguration = MutableLiveData<Event<Boolean>>()
    val firmId = MutableLiveData<Event<String>>()
    val itemDeleted = MutableLiveData<Event<Boolean>>()
    val readyToShare = MutableLiveData<Event<String>>()
    /** Logout Listener */
    val logoutStatus = MutableLiveData<Event<BaseModel>>()
    val refreshRequired = MutableLiveData<Event<Boolean>>()

    /**
     * Close the Application
     * */
    fun closeApplication(value: Boolean?) {
        value?.let {
            closeApp.postValue(Event(value))
        }
    }

    fun closeActivity(value: Boolean?) {
        value?.let {
            closeActivity.postValue(Event(value))
        }
    }

    fun refreshRequired(value: Boolean?) {
        value?.let {
            refreshRequired.postValue(Event(value))
        }
    }

    fun showShareDialog(value: String?) {
        value?.let {
            readyToShare.postValue(Event(value))
        }
    }

    /**
     * Show SnackBar Message
     * */
    fun showSnackMessage(message: String?) {
        if (!message.isNullOrEmpty())
            snackbarMessage.postValue(Event(message))
    }

    /**
     * Show ProgressBar
     * */
    fun showLoading(message: String? = null) {
        loadingDialogConfig.postValue(
            Event(
                LoadingUiConfig(
                    message ?: resourceProvider.getString(R.string.please_wait)
                )
            )
        )
    }

    /**
     * Hide ProgressBar
     * */
    fun dismissLoading() {
        loadingDialogConfig.postValue(Event(LoadingUiConfig()))
    }

    /**
     * Show Message Dialog as Requirement
     * */
    fun showMessageDialog(
        message: String? = null,
        title: String? = null,
        positiveClickTitle: String? = null,
        negativeClickTitle: String? = null,
        positiveClick: (() -> Unit)? = { dismissMessageDialog() },
        negativeClick: (() -> Unit)? = { dismissMessageDialog() },
        gravity: Int? = null/* Like Gravity.CENTER */,
        cancelable: Boolean? = null
    ) {
        errorDialogConfig.postValue(
            Event(
                DialogUiConfig(
                    title ?: resourceProvider.getString(R.string.error_title),
                    message ?: resourceProvider.getString(R.string.error_message),
                    positiveClickTitle ?: resourceProvider.getString(R.string.hint_ok),
                    negativeClickTitle ?: "",
                    gravity ?: Gravity.CENTER,
                    cancelable ?: false
                )
            )
        )
        errorDialogViewModel.postValue(Event(DialogViewModel(positiveClick, negativeClick)))
        isDialogVisible.postValue(Event(true))
    }

    /**
     * Dismiss Message Dialog
     * */
    fun dismissMessageDialog() {
        isDialogVisible.postValue(Event(false))
    }
}