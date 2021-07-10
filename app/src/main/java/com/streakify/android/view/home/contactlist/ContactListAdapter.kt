package com.streakify.android.view.home.contactlist

import com.streakify.android.R
import com.streakify.android.base.adapter.RecyclerViewAdapter
import com.streakify.android.utils.Util
import com.streakify.android.utils.livedata.Event
import com.streakify.android.utils.livedata.LiveEvent
import com.streakify.android.view.home.contactlist.data.Contact

class ContactListAdapter : RecyclerViewAdapter() {
    lateinit var partContactListEvent: LiveEvent<Event>

    companion object {
        const val VIEW_TYPE_ITEM = "VIEW_TYPE_ITEM"
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.contact_list_item
    }

    override fun getViewModel(position: Int): RecyclerViewItemViewModel {
        return ContactListItemVM(partContactListEvent, list[position].any as Contact)
    }

    override fun getItemId(position: Int): Long {
        var id = (list[position].any as? Contact)?.phone ?: ""
        id += (list[position].any as? Contact)?.name ?: ""
        return Util.getHash(id)
    }

    fun swapData(contacts: List<Contact>?) {
        list.clear()
        if ( !contacts.isNullOrEmpty() ) {
            contacts.forEach {
                list.add(RecyclerViewItem(VIEW_TYPE_ITEM, it))
            }
        }
        notifyDataSetChanged()
    }
}