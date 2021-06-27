package com.streakify.android.view.home.streaks.streaklist

import androidx.navigation.fragment.findNavController
import com.streakify.android.R
import com.streakify.android.application.AppConstants
import com.streakify.android.base.BaseFragment
import com.streakify.android.base.adapter.CommonAdapter
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.FragmentStreaksBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.streaks.StreaksEvent
import com.streakify.android.view.home.streaks.streaklist.data.StreaksItem
import kotlinx.android.synthetic.main.streak_list_item_definite.view.*
import javax.inject.Inject

class StreakListFragment : BaseFragment<FragmentStreaksBinding, StreakListVM>(),
    ItemClickListener<Any>, StreakListInterface {

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
            }
        }
    }

    override fun onItemClick(value: Any) {

    }

    override fun onPunchedIn(streak: StreaksItem?) {

    }

    override fun onPunchedOut(streak: StreaksItem?) {

    }
}