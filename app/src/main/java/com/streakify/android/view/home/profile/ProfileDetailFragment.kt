package com.streakify.android.view.home.profile

import android.content.Intent
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
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

        val info = requireContext().packageManager.getPackageInfo(
            requireContext().packageName, 0)
        val versionName = info.versionName

        viewModel.version.set(versionName)


        /* Set Observers to capture actions */
        bindObservers()
        viewModel.onAttach()
    }

    override fun handleEvent(event: Event) {
        when(event){
//            is ProfileEvents.ProfilePicEvent -> {
//                if(!event.imgUrl.isNullOrBlank()){
//                    Glide.with(this)
//                        .load(event.imgUrl)
//                        .apply( RequestOptions().placeholder(R.drawable.ic_profile)
//                        .error(R.drawable.ic_profile))
//                        .into(binding.contactImage)
//                }
//            }
            is ProfileEvents.LogoutEvent -> {
                findNavController().navigate(R.id.action_profileDetailFragment_to_splashFragment)
            }
        }
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {
        binding.tvInvite.setOnClickListener {
            invite()
        }

        binding.editProfile.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment, bundleOf("profile" to viewModel.profile))
        }
    }

    fun invite(){
        /*Create an ACTION_SEND Intent*/
        val intent = Intent(Intent.ACTION_SEND)
        /*This will be the actual content you wish you share.*/
        val shareBody = resourceProvider.getString(R.string.join_streakify)
        /*The type of the content is text, obviously.*/
        intent.type = "text/plain"
        /*Applying information Subject and Body.*/
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            getString(R.string.join_me_in_building_grate_habits)
        )
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        /*Fire!*/
        startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
    }
}