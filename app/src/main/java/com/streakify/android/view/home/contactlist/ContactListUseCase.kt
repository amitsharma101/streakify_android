package com.streakify.android.view.home.contactlist

import com.streakify.android.view.home.contactlist.data.Contact
import javax.inject.Inject

class ContactListUseCase @Inject constructor(private val contactRepository: ContactRepository) {
    suspend fun getContactListData(): List<Contact> {
        return contactRepository.getContacts()
    }
}