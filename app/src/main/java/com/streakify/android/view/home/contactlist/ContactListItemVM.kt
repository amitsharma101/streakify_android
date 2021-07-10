package com.streakify.android.view.home.contactlist

import com.streakify.android.base.adapter.RecyclerViewAdapter
import com.streakify.android.utils.livedata.Event
import com.streakify.android.utils.livedata.LiveEvent
import com.streakify.android.view.home.contactlist.data.Contact

class ContactListItemVM(private val partyContactListEvent: LiveEvent<Event>, val contact: Contact) :
    RecyclerViewAdapter.RecyclerViewItemViewModel {

    fun onContactSelected() {
        partyContactListEvent.value = ContactListEvent.ContactListClick(contact)
    }
}