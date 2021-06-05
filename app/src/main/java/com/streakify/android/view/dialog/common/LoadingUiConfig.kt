package com.streakify.android.view.dialog.common

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class LoadingUiConfig(
    val message: String ?= "",
) : Parcelable