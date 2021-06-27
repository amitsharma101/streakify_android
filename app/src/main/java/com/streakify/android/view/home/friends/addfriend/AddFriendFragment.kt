package com.streakify.android.view.home.friends.addfriend

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
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {

    }

    override fun handleEvent(event: Event) {
        when(event){

        }
    }

    override fun onItemClick(value: Any) {

    }
}