package com.example.streakify.view.activity

import com.example.streakify.base.BaseViewModel
import com.example.streakify.utils.network.NetworkLiveData
import com.example.streakify.view.dialog.common.EventListener
import com.khatabook.billsngst.commonrepo.CommonRepository
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val commonRepository: CommonRepository
) : BaseViewModel(networkLiveData)





