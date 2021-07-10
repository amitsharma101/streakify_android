package com.streakify.android.view.home.profile

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.EditProfileFragmentBinding
import com.streakify.android.databinding.EditStreakLayoutBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.image.FILE_NAME
import com.streakify.android.view.home.image.ImageBottomSheetFragment
import com.streakify.android.view.home.image.ImageSharedViewModel
import com.streakify.android.view.home.image.SOURCE
import javax.inject.Inject

const val PROFILE_PIC = "profile_pic"
const val EDIT_PROFILE = "edit_profile"
class EditProfileFragment : BaseFragment<EditProfileFragmentBinding, EditProfileVM>(){

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
        viewModel.profile.set(arguments?.getParcelable("profile"))

        binding.btnaddimage.setOnClickListener {
            showImageBottomSheetDialogFragment()
        }

        binding.btnimageedit.setOnClickListener {
            showImageBottomSheetDialogFragment()
        }

        /* Set Observers to capture actions */
        bindObservers()
        observeImageSelection()
    }

    private fun observeImageSelection() {
        val imageSharedViewModel = ViewModelProvider(requireActivity()).get(
            ImageSharedViewModel::class.java
        )

        imageSharedViewModel.selectedFileData.observe(viewLifecycleOwner, {
            it?.let {
                if(it.source== EDIT_PROFILE && it.fileUri!=null && it.fileName== PROFILE_PIC) {
                    viewModel.updateSelectedImage(requireContext(), it.fileUri, binding.img.width)
                }
            }
        })

        viewModel.selectedImageThumbnail.observe(viewLifecycleOwner, {

            if (it != null) {
                binding.img.setImageBitmap(it)
                binding.btnimageedit.visibility = View.VISIBLE
                binding.ivAddimage.visibility = View.GONE
                binding.textViewaddimage.visibility = View.GONE
            } else {
                binding.img.setImageBitmap(null)
                binding.btnimageedit.visibility = View.GONE
                binding.ivAddimage.visibility = View.VISIBLE
                binding.textViewaddimage.visibility = View.VISIBLE
            }
        })

    }

    private fun showImageBottomSheetDialogFragment() {
        val imageBottomSheetFragment = ImageBottomSheetFragment().apply {
            arguments= bundleOf().apply {
                putString(FILE_NAME, PROFILE_PIC)
                putString(SOURCE, EDIT_PROFILE)
            }
        }
        imageBottomSheetFragment.show(childFragmentManager, imageBottomSheetFragment.tag)
    }

    override fun handleEvent(event: Event) {
        when(event){
            is ProfileEvents.ProfileUpdatedEvent -> {
                requireActivity().onBackPressed()
            }
        }
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {
        binding.saveBtn.setOnClickListener {
            viewModel.updateProfile()
        }

        viewModel.selectedImageUrl.observe(this,{
            if (it != null) {
                Glide.with(this).load(it).into(binding.img)
                binding.btnimageedit.visibility = View.VISIBLE
                binding.ivAddimage.visibility = View.GONE
                binding.textViewaddimage.visibility = View.GONE
            } else {
                binding.img.setImageBitmap(null)
                binding.btnimageedit.visibility = View.GONE
                binding.ivAddimage.visibility = View.VISIBLE
                binding.textViewaddimage.visibility = View.VISIBLE
            }
        })
    }
}