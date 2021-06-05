package com.streakify.android.utils.livedata

import androidx.lifecycle.MediatorLiveData

class AbsentMediatorLiveData<T : Any?> : MediatorLiveData<T>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): AbsentMediatorLiveData<T> {
            return AbsentMediatorLiveData()
        }
    }
}