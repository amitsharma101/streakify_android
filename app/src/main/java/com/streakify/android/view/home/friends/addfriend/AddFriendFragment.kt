package com.streakify.android.view.home.friends.addfriend

import androidx.navigation.fragment.findNavController
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.base.adapter.CommonAdapter
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.AddFriendLayoutBinding
import com.streakify.android.databinding.FriendsListLayoutBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.friends.FriendsEvent
import com.streakify.android.view.home.friends.firendslist.FriendsListItemVM
import com.streakify.android.view.home.friends.firendslist.FriendsListVM
import com.streakify.android.view.home.friends.firendslist.PendingFriendsListItemVM
import javax.inject.Inject

const val SELECTED_CONTACT_NUMBER = "selected_contact_number"
class AddFriendFragment : BaseFragment<AddFriendLayoutBinding, AddFriendVM>(),
    ItemClickListener<Any> {

    companion object{
        private const val TAG = "StreakListFragment"
    }
    @Inject
    lateinit var resourceProvider: ResourceProvider

    /**
     * @return Layout Resource Such as R.layout.login_layout
     * */

    override fun setLayout() = R.layout.add_friend_layout

    /**
     * @return Class which extends {@link ViewModel}
     * */

    override fun setViewModel() = AddFriendVM::class.java

    /** Bind View with ViewModel */

    override fun bindViews() {

        binding.viewModel = viewModel

        /* Hide ActionBar */
//        (activity as MainActivity).hideActionBar()

        /* Set Observers to capture actions */
        bindObservers()

        viewModel.onAttach()

        val selectedContactNumber = arguments?.getString(SELECTED_CONTACT_NUMBER)
        if(!selectedContactNumber.isNullOrBlank()){
            viewModel.phone.set(selectedContactNumber)
        }
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {
        binding.send.setOnClickListener {
            viewModel.sendFriendRequest("+"+binding.countryCode.selectedCountryCode,binding.etPhone.text.toString())
        }

        viewModel.eventListener.closeActivity.observe(this, { event ->
            event?.getContentIfNotHandled()?.let {
                if (it) {
                    findNavController().popBackStack()
                }
            }
        })
    }

    override fun handleEvent(event: Event) {
        when(event){
            is FriendsEvent.FriendRequestSentEvent -> {
                findNavController().navigate(R.id.action_addFriendFragment_to_friendListFragment)
            }
        }
    }

    override fun onItemClick(value: Any) {

    }
}