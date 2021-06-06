package com.streakify.android.view.home.streaks.streaklist

import androidx.navigation.fragment.findNavController
import com.streakify.android.R
import com.streakify.android.application.AppConstants
import com.streakify.android.base.BaseFragment
import com.streakify.android.base.adapter.CommonAdapter
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.FragmentStreaksBinding
import com.streakify.android.di.provider.ResourceProvider
import kotlinx.android.synthetic.main.streak_list_item_definite.view.*
import javax.inject.Inject

class StreakListFragment : BaseFragment<FragmentStreaksBinding, StreakListVM>(),
    ItemClickListener<Any> {

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

        adapter = CommonAdapter(initData(), this)
        binding.recyclerView.adapter = adapter

        /* Set Observers to capture actions */
        bindObservers()
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {
        binding.addGoal.setOnClickListener {
            findNavController().navigate(R.id.editStreakFragment)
        }
    }

    fun initData():MutableList<StreakListItemVM>{
        val streaks = mutableListOf<StreakListItemVM>()

        val streak1 = Streak(type = AppConstants.STREAK_TYPE_INDEFINITE,name = "Wake Up Early")
        streaks.add(StreakListItemVM(streak1))

        val streak2 = Streak(type = AppConstants.STREAK_TYPE_DEFINITE,name = "Gym Everyday")
        streaks.add(StreakListItemVM(streak2))

        val streak3 = Streak(type = AppConstants.STREAK_TYPE_INDEFINITE, name = "Study Everyday")
        streaks.add(StreakListItemVM(streak3))

        return streaks
    }

    override fun onItemClick(value: Any) {

    }
}