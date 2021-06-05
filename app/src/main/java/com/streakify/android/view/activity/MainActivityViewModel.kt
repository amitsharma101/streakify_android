package com.streakify.android.view.activity

import com.streakify.android.base.BaseViewModel
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.khatabook.billsngst.commonrepo.CommonRepository
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val commonRepository: CommonRepository
) : BaseViewModel(networkLiveData)





