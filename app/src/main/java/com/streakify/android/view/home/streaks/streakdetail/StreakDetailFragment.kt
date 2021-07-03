package com.streakify.android.view.home.streaks.streakdetail

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.StreakDetailFragmentBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.view.home.streaks.streaklist.StreakListItemVM
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


    /** Bind View with ViewModel */

    override fun bindViews() {

        binding.viewModel = viewModel

        /* Set Observers to capture actions */
        bindObservers()
        streakId = arguments?.getInt(STREAK_ID)!!
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

    override fun onItemClick(value: Any) {

    }
}