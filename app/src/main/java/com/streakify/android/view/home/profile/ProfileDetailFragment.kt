package com.streakify.android.view.home.profile

import android.content.res.ColorStateList
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.EditProfileFragmentBinding
import com.streakify.android.databinding.EditStreakLayoutBinding
import com.streakify.android.databinding.ProfileDetailFragmentBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.livedata.Event
import javax.inject.Inject

class ProfileDetailFragment : BaseFragment<ProfileDetailFragmentBinding, ProfileDetailVM>(){

    companion object{
        private const val TAG = "ProfileDetailFragment"
    }
    @Inject
    lateinit var resourceProvider: ResourceProvider

    /**
     * @return Layout Resource Such as R.layout.login_layout
     * */

    override fun setLayout() = R.layout.profile_detail_fragment

    /**
     * @return Class which extends {@link ViewModel}
     * */

    override fun setViewModel() = ProfileDetailVM::class.java


    /** Bind View with ViewModel */

    override fun bindViews() {

        binding.viewModel = viewModel

        /* Set Observers to capture actions */
        bindObservers()
        viewModel.onAttach()
    }

    override fun handleEvent(event: Event) {
        when(event){
            is ProfileEvents.ProfilePicEvent -> {
                if(!event.imgUrl.isNullOrBlank()){
                    Glide.with(this)
                        .load(event.imgUrl)
                        .apply( RequestOptions().placeholder(R.drawable.ic_profile)
                        .error(R.drawable.ic_profile))
                        .into(binding.contactImage)
                }
            }
        }
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {
        binding.editProfile.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment, bundleOf("profile" to viewModel.profile))
        }
    }
}