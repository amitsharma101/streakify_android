package com.streakify.android.view.home.friends.firendslist

import android.view.View
import androidx.navigation.fragment.findNavController
import com.streakify.android.R
import com.streakify.android.application.AppConstants
import com.streakify.android.base.BaseFragment
import com.streakify.android.base.adapter.CommonAdapter
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.FriendsListLayoutBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.friends.FriendsEvent
import com.streakify.android.view.home.friends.firendslist.data.ActiveFriendsItem
import com.streakify.android.view.home.friends.firendslist.data.PendingFriendsItem
import javax.inject.Inject

class FriendsListFragment : BaseFragment<FriendsListLayoutBinding, FriendsListVM>(),
    ItemClickListener<Any>, FriendRequestAction, FriendsListInterface {

    companion object{
        private const val TAG = "StreakListFragment"
    }
    @Inject
    lateinit var resourceProvider: ResourceProvider

    /**
     * @return Layout Resource Such as R.layout.login_layout
     * */

    override fun setLayout() = R.layout.friends_list_layout

    /**
     * @return Class which extends {@link ViewModel}
     * */

    override fun setViewModel() = FriendsListVM::class.java

    private lateinit var adapterFriends: CommonAdapter<FriendsListItemVM>

    /** Bind View with ViewModel */

    override fun bindViews() {

        binding.viewModel = viewModel

        /* Hide ActionBar */
//        (activity as MainActivity).hideActionBar()
        adapterFriends = CommonAdapter(emptyList(), this)

        binding.rvFriends.adapter = adapterFriends

        /* Set Observers to capture actions */
        bindObservers()

        viewModel.onAttach()
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {
        binding.seeAll.setOnClickListener{
            findNavController().navigate(R.id.friendRequestFragment)
        }
    }

    override fun handleEvent(event: Event) {
        when(event){
            is FriendsEvent.FriendsListFetchedEvent -> {
                val friends = event.friendsList
                val friendsListAdapter = mutableListOf<FriendsListItemVM>()
                friends?.forEach {
                    friendsListAdapter.add(
                        FriendsListItemVM(it!!,this)
                    )
                }
                adapterFriends.items = friendsListAdapter
                adapterFriends.notifyDataSetChanged()
            }
            is FriendsEvent.PendingFriendsListFetchedEvent -> {
                val pendingFriends = event.pendingFriendsList

                if(pendingFriends.isNullOrEmpty()){
                    viewModel.friendRequestVisibility.set(View.GONE)
                }
                else{
                    viewModel.friendRequestVisibility.set(View.VISIBLE)
                    val friendRequest = pendingFriends[0]
                    binding.friendRequest.data = PendingFriendsListItemVM(friendRequest!!,this)
                }
            }
        }
    }

    override fun onItemClick(value: Any) {

    }

    override fun accept(pendingFriend: PendingFriendsItem) {
        viewModel.friendRequestAction(pendingFriend,AppConstants.ACCEPT_FRIEND_REQUEST)
    }

    override fun reject(pendingFriend: PendingFriendsItem) {
        viewModel.friendRequestAction(pendingFriend,AppConstants.REJECT_FRIEND_REQUEST)
    }

    override fun onFriendRemove(activeFriend: ActiveFriendsItem) {
        viewModel.removeFriend(activeFriend)
    }
}