package com.streakify.android.view.home.contactlist

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.databinding.FragmentContactListBinding
import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.contactlist.data.Contact
import com.streakify.android.view.home.friends.addfriend.SELECTED_CONTACT_NUMBER

const val REQUEST_CODE_CONTACT: Int = 101
const val SELECTED_CONTACT = "SELECTED_CONTACT"
class ContactListFragment : BaseFragment<FragmentContactListBinding, ContactListVM>() {

    override fun setLayout(): Int {
        return R.layout.fragment_contact_list
    }

    override fun bindViews() {
        viewModel.adapter.partContactListEvent = viewModel.event
        binding.viewModel = viewModel
        binding.recyclerView.adapter = viewModel.adapter
        binding.lifecycleOwner = this

        binding.simpleSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.onTextChange(newText)
                }
                return true
            }

        })
        requestPermissionIfNeeded()
    }

    override fun onResume() {
        super.onResume()
        initialiseViewModel()
    }

    override fun setViewModel() = ContactListVM::class.java

    override fun handleEvent(event: Event) {
        super.handleEvent(event)
        when (event) {
            is ContactListEvent.ContactListClick -> onContactSelected(event.contact)
            is ContactListEvent.OnPermissionSettingClick -> onGivePermissionClick()
            is ContactListEvent.OnSkipClick -> onSkipClick()
            is ContactListEvent.AddFriendManuallyClicked -> addFriendManually()
        }
    }

    private fun addFriendManually() {
        findNavController().navigate(R.id.addFriendFragment)
    }


    private fun onContactSelected(contact: Contact) {
        val bundle = bundleOf(SELECTED_CONTACT_NUMBER to contact.phone)
        findNavController().navigate(R.id.addFriendFragment, bundle)
    }

    private fun requestPermissionIfNeeded() {
        if (!Permissions(requireContext()).isContactPermissionGiven()) {
            requestPermissions(
                arrayOf(
//                    android.Manifest.permission.WRITE_CONTACTS,
                    android.Manifest.permission.READ_CONTACTS
                ), REQUEST_CODE_CONTACT
            )
        }
    }

    private fun initialiseViewModel() {
        if (Permissions(requireContext()).isContactPermissionGiven()) {
            viewModel.initialisation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_CONTACT) {
            if (!Permissions(requireContext()).isContactPermissionGiven()) {
                viewModel.setPermissionDeniedLayout()
            } else {
                viewModel.initialisation()
            }
        }
    }

    private fun onGivePermissionClick() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", activity?.packageName, null)
        intent.data = uri
        context?.startActivity(intent)
    }

    private fun onSkipClick() {
        findNavController().popBackStack()
    }
}
