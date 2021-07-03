package com.streakify.android.view.home.streaks.streakdetail

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.base.adapter.CommonAdapter
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.StreakDetailFragmentBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.streaks.StreaksEvent
import javax.inject.Inject


const val STREAK_ID = "streak_id"
class StreakDetailFragment : BaseFragment<StreakDetailFragmentBinding, StreakDetailVM>(),
    ItemClickListener<Any> {

    companion object{
        private const val TAG = "StreakDetailFragment"
    }
    @Inject
    lateinit var resourceProvider: ResourceProvider

    /**
     * @return Layout Resource Such as R.layout.login_layout
     * */

    override fun setLayout() = R.layout.streak_detail_fragment

    /**
     * @return Class which extends {@link ViewModel}
     * */

    override fun setViewModel() = StreakDetailVM::class.java

    var streakId = -1
    lateinit var adapter : CommonAdapter<StreakDetailFriendItemVM>


    /** Bind View with ViewModel */

    override fun bindViews() {

        binding.viewModel = viewModel

        /* Set Observers to capture actions */
        bindObservers()
        streakId = arguments?.getInt(STREAK_ID)!!

        adapter = CommonAdapter(emptyList(),this)
        binding.rvFriends.adapter = adapter

        viewModel.onAttach(
            streakId
        )
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {
        binding.edit.setOnClickListener {
            findNavController().navigate(R.id.editStreakFragment, bundleOf(STREAK_ID to streakId))
        }
    }

    override fun handleEvent(event: Event) {
        when(event){
            is StreaksEvent.StreakDetailParticipantsFetched -> {
                val participants = event.participants
                val adapterList = mutableListOf<StreakDetailFriendItemVM>()
                participants?.forEach {
                    adapterList.add(StreakDetailFriendItemVM(it!!,
                        viewModel.streakDetails?.type!!,
                        viewModel.streakDetails?.maxDuration?:0,
                    resourceProvider),
                    )
                }
                adapter.items = adapterList
                adapter.notifyDataSetChanged()
            }
            is StreaksEvent.DefinitePercentage -> {
                binding.progress.setProgress(event.per.toInt())
            }
        }
    }

    override fun onItemClick(value: Any) {

    }
}