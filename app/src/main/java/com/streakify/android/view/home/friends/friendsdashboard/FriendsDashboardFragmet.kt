package com.streakify.android.view.home.friends.friendsdashboard

import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.databinding.FragmentFriendsDashboardBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.view.activity.MainActivity
import com.streakify.android.view.home.friends.firendrequests.FriendRequestFragment
import com.streakify.android.view.home.friends.firendslist.FriendsListFragment
import javax.inject.Inject

class FriendsDashboardFragment : BaseFragment<FragmentFriendsDashboardBinding, FriendsDashboardVM>() {

    /**
     * Inject Resource Provider Class
     * */
    @Inject
    lateinit var resourceProvider: ResourceProvider

    /**
     * @return Layout Resource Such as R.layout.fragment_dashboard
     * */
    override fun setLayout(): Int = R.layout.fragment_friends_dashboard

    /**
     * @return Class which extends {@link ViewModel}
     * */
    override fun setViewModel(): Class<FriendsDashboardVM> = FriendsDashboardVM::class.java

    /**
     * Bind Views with ViewModel
     * */
    override fun bindViews() {

        binding.viewModel = viewModel

        /* Show ActionBar */
        (activity as MainActivity).showActionBar()


        /* view pager setup*/
        val adapter = MyAdapter(childFragmentManager)
        adapter.addFragment(FriendsListFragment(), getString(R.string.friends))
        adapter.addFragment(FriendRequestFragment(), getString(R.string.friend_requests))
        binding.viewPager.offscreenPageLimit = 2
        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        clickListeners()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        /* Set Observers to capture actions */
        bindObservers()
    }

    private fun bindObservers() {


        /* Observe Network Connection State */
//        viewModel.mNetworkErrorData.observe(viewLifecycleOwner,
//            {
//                if (LoadingState.IDLE === viewModel.mLoadingStateData.value?.peekContent()) {
//                    if (it.status) {
////                        viewModel.checkFirmsCount()
//                        return@observe
//                    }
//                }
//            })
    }

    private fun clickListeners() {
        binding.addFriend.setOnClickListener {
            findNavController().navigate(R.id.contactListFragment)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if ((requestCode == REQUEST_CODE_NEW_PAYMENT
//                    || requestCode == REQUEST_CODE_NEW_INVOICE
//                    || requestCode == REQUEST_CODE_ADD_PARTY)
//            && resultCode == Activity.RESULT_OK
//        ) {
//            viewModel.refreshAggregates()
//            (binding.viewPager.adapter as MyAdapter).mFragmentList.forEach {
//                (it as DashboardChildren).refreshList()
//            }
//        } else super.onActivityResult(requestCode, resultCode, data)
//    }
}