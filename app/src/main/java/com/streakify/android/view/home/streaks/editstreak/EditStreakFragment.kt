package com.streakify.android.view.home.streaks.editstreak

import androidx.navigation.fragment.findNavController
import com.streakify.android.R
import com.streakify.android.application.AppConstants
import com.streakify.android.base.BaseFragment
import com.streakify.android.base.adapter.CommonAdapter
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.EditStreakLayoutBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.view.home.streaks.streaklist.Streak
import com.streakify.android.view.home.streaks.streaklist.StreakListItemVM
import com.streakify.android.view.home.streaks.streaklist.StreakListVM
import javax.inject.Inject

class EditStreakFragment : BaseFragment<EditStreakLayoutBinding, EditStreakVM>(),
    ItemClickListener<Any> {

    companion object{
        private const val TAG = "EditStreakFragment"
    }
    @Inject
    lateinit var resourceProvider: ResourceProvider

    /**
     * @return Layout Resource Such as R.layout.login_layout
     * */

    override fun setLayout() = R.layout.edit_streak_layout

    /**
     * @return Class which extends {@link ViewModel}
     * */

    override fun setViewModel() = EditStreakVM::class.java


    /** Bind View with ViewModel */

    override fun bindViews() {

        binding.viewModel = viewModel

        /* Set Observers to capture actions */
        bindObservers()
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {
        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onItemClick(value: Any) {

    }
}