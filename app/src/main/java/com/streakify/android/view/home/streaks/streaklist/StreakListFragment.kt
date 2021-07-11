package com.streakify.android.view.home.streaks.streaklist

import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.streakify.android.R
import com.streakify.android.application.AppConstants
import com.streakify.android.base.BaseFragment
import com.streakify.android.base.adapter.CommonAdapter
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.FragmentStreaksBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.streaks.StreaksEvent
import com.streakify.android.view.home.streaks.streakdetail.STREAK_ID
import com.streakify.android.view.home.streaks.streaklist.data.StreaksItem
import kotlinx.android.synthetic.main.streak_list_item_definite.view.*
import javax.inject.Inject

class StreakListFragment : BaseFragment<FragmentStreaksBinding, StreakListVM>(),
    ItemClickListener<StreakListItemVM>, StreakListInterface {

    companion object{
        private const val TAG = "StreakListFragment"
    }
    @Inject
    lateinit var resourceProvider: ResourceProvider

    /**
     * @return Layout Resource Such as R.layout.login_layout
     * */

    override fun setLayout() = R.layout.fragment_streaks

    /**
     * @return Class which extends {@link ViewModel}
     * */

    override fun setViewModel() = StreakListVM::class.java

    private lateinit var adapter: CommonAdapter<StreakListItemVM>

    /** Bind View with ViewModel */

    override fun bindViews() {

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(TAG, msg)
        })


        binding.viewModel = viewModel

        /* Hide ActionBar */
//        (activity as MainActivity).hideActionBar()

        adapter = CommonAdapter(emptyList(), this)
        binding.recyclerView.adapter = adapter

        /* Set Observers to capture actions */
        bindObservers()

        viewModel.onAttach()
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.onAttach()
        }
        binding.addGoal.setOnClickListener {
            findNavController().navigate(R.id.editStreakFragment)
        }
    }

    override fun handleEvent(event: Event) {
        when(event){
            is StreaksEvent.StreakListFetchedEvent -> {
                val streaksList = mutableListOf<StreakListItemVM>()
                event.streakList?.forEach {
                    streaksList.add(StreakListItemVM(it,resourceProvider,this))
                }
                adapter.items = streaksList
                adapter.notifyDataSetChanged()

                binding.swipeLayout.isRefreshing = false
            }
        }
    }

    override fun onPunchedIn(streak: StreaksItem?) {
        viewModel.punch(streak,true)
    }

    override fun onPunchedOut(streak: StreaksItem?) {
        viewModel.punch(streak,false)
    }

    override fun onItemClick(value: StreakListItemVM) {
        val bundle = bundleOf(STREAK_ID to value.streak?.streakId)
        findNavController().navigate(R.id.streakDetailFragment,bundle)
    }
}