package com.streakify.android.view.home.contactlist.data

import java.io.Serializable

data class Contact(
    val name: String,
    val phone: String,
    val photoUri: String?,
    val purifiedNumber: String?
) : Serializable