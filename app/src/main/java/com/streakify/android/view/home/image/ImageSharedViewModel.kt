package com.streakify.android.view.home.image

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImageSharedViewModel : ViewModel() {
    var selectedFileData = MutableLiveData<ImageBottomSheetFragment.SharedFileData?>()
}
