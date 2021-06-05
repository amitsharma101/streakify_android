package com.example.streakify.view.dialog.common

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class DialogUiConfig(
    val title: String,
    val message: String,
    val positiveButtonText: String,
    val negativeButtonText: String,
    val gravity: Int,
    val cancelable: Boolean
) : Parcelable