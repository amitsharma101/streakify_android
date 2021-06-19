package com.streakify.android.view.home.profile

import android.content.res.ColorStateList
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.EditProfileFragmentBinding
import com.streakify.android.databinding.EditStreakLayoutBinding
import com.streakify.android.di.provider.ResourceProvider
import javax.inject.Inject

class EditProfileFragment : BaseFragment<EditProfileFragmentBinding, EditProfileVM>(),
    ItemClickListener<Any> {

    companion object{
        private const val TAG = "EditStreakFragment"
    }
    @Inject
    lateinit var resourceProvider: ResourceProvider

    /**
     * @return Layout Resource Such as R.layout.login_layout
     * */

    override fun setLayout() = R.layout.edit_profile_fragment

    /**
     * @return Class which extends {@link ViewModel}
     * */

    override fun setViewModel() = EditProfileVM::class.java


    /** Bind View with ViewModel */

    override fun bindViews() {

        binding.viewModel = viewModel

        /* Set Observers to capture actions */
        bindObservers()
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {

    }

    override fun onItemClick(value: Any) {

    }
}