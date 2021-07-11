package com.streakify.android.view.home.friends.firendrequests

import com.streakify.android.R
import com.streakify.android.application.AppConstants
import com.streakify.android.base.BaseFragment
import com.streakify.android.base.adapter.CommonAdapter
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.FriendRequestsLayoutBinding
import com.streakify.android.databinding.FriendsListLayoutBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.friends.FriendsEvent
import com.streakify.android.view.home.friends.firendslist.FriendRequestAction
import com.streakify.android.view.home.friends.firendslist.PendingFriendsListItemVM
import com.streakify.android.view.home.friends.firendslist.data.PendingFriendsItem
import javax.inject.Inject

class FriendRequestFragment : BaseFragment<FriendRequestsLayoutBinding, FriendRequestsVM>(),
    ItemClickListener<Any>, FriendRequestAction {

    companion object{
        private const val TAG = "FriendRequestFragment"
    }
    @Inject
    lateinit var resourceProvider: ResourceProvider

    /**
     * @return Layout Resource Such as R.layout.login_layout
     * */

    override fun setLayout() = R.layout.friend_requests_layout

    /**
     * @return Class which extends {@link ViewModel}
     * */

    override fun setViewModel() = FriendRequestsVM::class.java

    private lateinit var adapterFriendRequests: CommonAdapter<PendingFriendsListItemVM>

    /** Bind View with ViewModel */

    override fun bindViews() {

        binding.viewModel = viewModel

        /* Hide ActionBar */
//        (activity as MainActivity).hideActionBar()

        adapterFriendRequests = CommonAdapter(emptyList(), this)
        binding.rvFriendRequests.adapter = adapterFriendRequests

        /* Set Observers to capture actions */
        bindObservers()

        viewModel.onAttach()
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.onAttach()
        }
    }

    override fun handleEvent(event: Event) {
        when(event){
            is FriendsEvent.PendingFriendsListFetchedEvent -> {
                val pendingFriends = event.pendingFriendsList
                val pendingFriendsListAdapter = mutableListOf<PendingFriendsListItemVM>()
                pendingFriends?.forEach {
                    pendingFriendsListAdapter.add(
                        PendingFriendsListItemVM(it!!,this)
                    )
                }
                adapterFriendRequests.items = pendingFriendsListAdapter
                adapterFriendRequests.notifyDataSetChanged()

                binding.swipeLayout.isRefreshing = false
            }
        }
    }

    override fun onItemClick(value: Any) {

    }

    override fun accept(pendingFriend: PendingFriendsItem) {
        viewModel.friendRequestAction(pendingFriend, AppConstants.ACCEPT_FRIEND_REQUEST)
    }

    override fun reject(pendingFriend: PendingFriendsItem) {
        viewModel.friendRequestAction(pendingFriend, AppConstants.REJECT_FRIEND_REQUEST)
    }
}