package com.streakify.android.view.home.contactlist

import com.streakify.android.view.home.contactlist.data.Contact

interface ContactRepository  {
    suspend fun getContacts(): List<Contact>
}