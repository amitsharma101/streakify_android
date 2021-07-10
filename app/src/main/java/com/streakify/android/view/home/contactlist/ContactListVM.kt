package com.streakify.android.view.home.contactlist

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.streakify.android.base.BaseViewModel
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.home.contactlist.data.Contact
import kotlinx.coroutines.*
import javax.inject.Inject


class ContactListVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val context: Context
) : BaseViewModel(networkLiveData) {
    var isPermissionLayoutVisible = ObservableBoolean(false)
    val adapter: ContactListAdapter = ContactListAdapter()
    var contacts: List<Contact>? = emptyList()
    val _filterString: MutableLiveData<String> = MutableLiveData("")
    val filterString: LiveData<String> = _filterString
    var filterTask: Deferred<List<Contact>>? = null

    fun initialisation() {
        unSetPermissionDeniedLayout()
        val contactListUseCase: ContactListUseCase =
            ContactListUseCase(CContactRepository(context))
        viewModelScope.launch {
            refreshContacts(contactListUseCase.getContactListData())
        }
    }

    fun onGivePermissionClick() {
        event.value = ContactListEvent.OnPermissionSettingClick
    }

    fun onSkipClick() {
        event.value = ContactListEvent.OnSkipClick
    }

    fun onAddFriendManuallyClicked(){
        event.value = ContactListEvent.AddFriendManuallyClicked
    }

    fun setPermissionDeniedLayout() {
        isPermissionLayoutVisible.set(true)
    }

    fun unSetPermissionDeniedLayout() {
        isPermissionLayoutVisible.set(false)
    }

    private fun refreshContacts(contacts: List<Contact>?) {
        adapter.swapData(contacts)
        this.contacts = contacts
    }

    suspend fun filterResults(filter: String?): List<Contact> {
        if (contacts == null) {
            return emptyList()
        }
        return withContext(Dispatchers.IO) {
            val filteredNamesMiddle = ArrayList<Contact>()
            val filteredNamesBottom = ArrayList<Contact>()
            val filteredNamesTop = ArrayList<Contact>()
            val filteredNamesTop2 = ArrayList<Contact>()
            if (filter != null && filter.isNotEmpty()) {
                for (s in contacts!!) {
                    val lowerCaseFilter = filter.toLowerCase()
                    val lowerCaseName = s.name?.toLowerCase() ?: ""
                    val match = matchName(filter, lowerCaseName)
                    if (match == 1) {
                        filteredNamesTop.add(s)
                    } else if (match == 2) {
                        filteredNamesTop2.add(s)
                    } else if (lowerCaseName.contains(lowerCaseFilter) || s.phone?.contains(
                            lowerCaseFilter
                        )
                    ) {
                        filteredNamesMiddle.add(s)
                    }
                }

                filteredNamesTop.addAll(filteredNamesTop2)
                filteredNamesTop.addAll(filteredNamesMiddle)
                filteredNamesTop.addAll(filteredNamesBottom)
            } else {
                filteredNamesTop.addAll(contacts!!)
            }
            return@withContext filteredNamesTop
        }
    }

    private fun matchName(filter: String, name: String): Int {
        if (filter.isBlank() || name.isBlank()) {
            return 0
        }

        val nameParts = name.split(" ")
        nameParts.forEachIndexed { index, s ->
            if (s.indexOf(filter, 0, true) == 0) {
                return if (index == 0) 1 else 2
            }
        }
        return 0
    }

    fun onTextChange(text: CharSequence) {
        val filter = text.toString()
        if (filter != filterString.value) {
            _filterString.value = filter
            filterData()
        }
    }

    private fun filterData() {
        viewModelScope.launch {
            if (filterTask != null) {
                filterTask?.cancel()
            }
            filterTask = async(Dispatchers.IO) { filterResults(_filterString.value) }
            adapter.swapData(filterTask?.await())
        }
    }
}