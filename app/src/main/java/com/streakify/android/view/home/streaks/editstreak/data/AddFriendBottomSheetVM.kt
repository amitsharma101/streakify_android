package com.streakify.android.view.home.streaks.editstreak.data

import androidx.databinding.ObservableField
import com.streakify.android.R
import com.streakify.android.base.BaseViewModel
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.network.NetworkLiveData
import javax.inject.Inject

class AddFriendBottomSheetVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val resourceProvider: ResourceProvider
) : BaseViewModel(networkLiveData) {

    val title = ObservableField(resourceProvider.getString(R.string.select_friends))
    val hintText = ObservableField(resourceProvider.getString(R.string.search))
}