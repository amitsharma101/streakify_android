package com.example.streakify.base

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.streakify.utils.livedata.LiveEvent
import com.example.streakify.utils.network.ConnectivityState
import com.example.streakify.utils.network.NetworkLiveData
import com.example.streakify.utils.network.NetworkMessage


abstract class BaseViewModel (
    networkLiveData: NetworkLiveData,
) : ViewModel() {

    var event = LiveEvent<com.example.streakify.utils.livedata.Event>()

    /** Loading Detection */
    val mLoadingStateData = MutableLiveData(Event(LoadingState.IDLE))

    /** Update When Need to Move Next Destination */
    val mDestinationForward = MutableLiveData<Event<Any>>()

    /**
     * Network Connection/Disconnect Detection
     * */
    var mErrorModel = ObservableField(BaseModel(true))
    val mNetworkErrorData = MutableLiveData<BaseModel>()

    /** Error View: Retry Click */
    open fun onErrorButtonClick(){}

    init {
        /* Observer ConnectivityState Change */
        networkLiveData.observeForever {
            it?.let { onNetworkState(it) }
        }

        /* Need to Notify First Time
        *  Because NetworkLiveData doesn't send callback if network is disconnected
        *  while launching the app */
        networkLiveData.getConnectivityState().let {
            if (it == ConnectivityState.DISCONNECTED)
                onNetworkState(it)
        }
    }

    /**
     * Update LiveData when State change
     * */
    private fun onNetworkState(connectivityState: ConnectivityState) {
        when (connectivityState) {
            ConnectivityState.WIFI_CONNECTED,
            ConnectivityState.MOBILE_DATA_CONNECTED -> {
                mNetworkErrorData.postValue(BaseModel(true,
                        NetworkMessage.NETWORK_CONNECTED))
            }

            ConnectivityState.DISCONNECTED -> {
                mNetworkErrorData.postValue(BaseModel(message = NetworkMessage.NO_NETWORK))
            }
        }
    }

}