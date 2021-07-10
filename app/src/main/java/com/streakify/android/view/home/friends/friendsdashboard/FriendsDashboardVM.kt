package com.streakify.android.view.home.friends.friendsdashboard

import com.streakify.android.base.BaseViewModel
import com.streakify.android.commonrepo.CommonRepository
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.network.NetworkLiveData
import java.util.*
import javax.inject.Inject

class FriendsDashboardVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val commonRepository: CommonRepository,
    val resourceProvider: ResourceProvider,
) : BaseViewModel(networkLiveData) {


}