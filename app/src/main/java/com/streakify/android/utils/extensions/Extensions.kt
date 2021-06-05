package com.streakify.android.utils.extensions

import androidx.databinding.Observable

class Extensions {
    companion object{
        fun Observable.addPropertyChangedCallback(doThis: () -> Unit) {
            addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    doThis.invoke()
                }
            })

        }
    }
}