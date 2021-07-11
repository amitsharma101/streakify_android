package com.streakify.android.view.home.streaks.editstreak

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.base.adapter.CommonAdapter
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.EditStreakLayoutBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.friends.firendslist.FriendsListItemVM
import com.streakify.android.view.home.friends.firendslist.data.ActiveFriendsItem
import com.streakify.android.view.home.image.FILE_NAME
import com.streakify.android.view.home.image.ImageBottomSheetFragment
import com.streakify.android.view.home.image.ImageSharedViewModel
import com.streakify.android.view.home.image.SOURCE
import com.streakify.android.view.home.profile.EDIT_PROFILE
import com.streakify.android.view.home.profile.PROFILE_PIC
import com.streakify.android.view.home.streaks.StreaksEvent
import com.streakify.android.view.home.streaks.editstreak.data.AddFriendBSInterface
import com.streakify.android.view.home.streaks.editstreak.data.AddFriendBSItemVM
import com.streakify.android.view.home.streaks.editstreak.data.AddFriendBottomSheet
import javax.inject.Inject

const val EDIT_STREAK = "edit_streak"
const val STREAK_PIC = "streak_pic"

const val BOTTOM_SHEET_TAG = "bs"
class EditStreakFragment : BaseFragment<EditStreakLayoutBinding, EditStreakVM>(),
    ItemClickListener<Any>, AddFriendBSInterface, AddedFriendInterface {

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

    private lateinit var adapter: CommonAdapter<AddedFriendEditStreakItemVM>


    /** Bind View with ViewModel */

    override fun bindViews() {

        binding.viewModel = viewModel

        /* Set Observers to capture actions */
        bindObservers()
        adapter = CommonAdapter(emptyList(),this)
        binding.rvFriends.adapter = adapter
        viewModel.onAttach(arguments?.getInt("streak_id")?:-1)

        binding.btnaddimage.setOnClickListener {
            showImageBottomSheetDialogFragment()
        }

        binding.btnimageedit.setOnClickListener {
            showImageBottomSheetDialogFragment()
        }

        observeImageSelection()
    }

    private fun observeImageSelection() {
        val imageSharedViewModel = ViewModelProvider(requireActivity()).get(
            ImageSharedViewModel::class.java
        )

        imageSharedViewModel.selectedFileData.observe(viewLifecycleOwner, {
            it?.let {
                if(it.source== EDIT_STREAK && it.fileUri!=null && it.fileName== STREAK_PIC) {
                    viewModel.updateSelectedImage(requireContext(), it.fileUri, binding.img.width)
                }
            }
        })

        viewModel.selectedImageThumbnail.observe(viewLifecycleOwner, {

            if (it != null) {
                binding.img.setImageBitmap(it)
                binding.btnimageedit.visibility = View.VISIBLE
                binding.ivAddimage.visibility = View.GONE
            } else {
                binding.img.setImageBitmap(null)
                binding.btnimageedit.visibility = View.GONE
                binding.ivAddimage.visibility = View.VISIBLE
            }
        })

    }

    private fun showImageBottomSheetDialogFragment() {
        val imageBottomSheetFragment = ImageBottomSheetFragment().apply {
            arguments= bundleOf().apply {
                putString(FILE_NAME, STREAK_PIC)
                putString(SOURCE, EDIT_STREAK)
            }
        }
        imageBottomSheetFragment.show(childFragmentManager, imageBottomSheetFragment.tag)
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {
        binding.addFriend.setOnClickListener {
            showBottomSheetDialogFragment(AddFriendBottomSheet(viewModel.friends,this))
        }


        viewModel.eventListener.closeActivity.observe(this, { event ->
            event?.getContentIfNotHandled()?.let {
                if (it) {
                    findNavController().popBackStack()
                }
            }
        })

        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.selectedImageUrl.observe(this,{
            if (it != null) {
                Glide.with(this).load(it).into(binding.img)
                binding.btnimageedit.visibility = View.VISIBLE
                binding.ivAddimage.visibility = View.GONE
            } else {
                binding.img.setImageBitmap(null)
                binding.btnimageedit.visibility = View.GONE
                binding.ivAddimage.visibility = View.VISIBLE
            }
        })
    }

    override fun handleEvent(event: Event) {
        when(event){
            is StreaksEvent.DeifiniteClickedEvent -> {
                onDefiniteClicked()
            }
            is StreaksEvent.IndeifiniteClickedEvent -> {
                onIndefiniteClicked()
            }
            is StreaksEvent.FriendAddedEvent -> {
                val lst = viewModel.frindsInStreak.filter { !it.isDeleted }
                val adapterItems = mutableListOf<AddedFriendEditStreakItemVM>()
                lst.forEach {
                    adapterItems.add(AddedFriendEditStreakItemVM(it!!,this))
                }
                adapter.items = adapterItems
                adapter.notifyDataSetChanged()
            }
            is StreaksEvent.StreakDeleted -> {
                findNavController().navigate(R.id.action_editStreakFragment_to_streakListFragment)
            }
        }
    }

    fun onDefiniteClicked(){
        select(binding.textViewdefinite)
        unSelect(binding.textViewindefinite)
    }

    fun onIndefiniteClicked(){
        select(binding.textViewindefinite)
        unSelect(binding.textViewdefinite)
    }

    private fun select(textView: TextView) {
        textView.backgroundTintList = ColorStateList.valueOf(resourceProvider.getColor(R.color.colorPrimaryDark))
        textView.setTextColor(resourceProvider.getColor(R.color.colorWhite))
    }

    private fun unSelect(textView: TextView) {
        textView.backgroundTintList = ColorStateList.valueOf(resourceProvider.getColor(R.color.colorWhite))
        textView.setTextColor(resourceProvider.getColor(R.color.colorGrey))
    }

    override fun onItemClick(value: Any) {

    }


    private fun showBottomSheetDialogFragment(bottomSheetFragment: BottomSheetDialogFragment) {
        bottomSheetFragment.show(childFragmentManager, BOTTOM_SHEET_TAG)
    }

    override fun onFriendClicked(value: AddFriendBSItemVM) {
        viewModel.onFriendClicked(value)
    }

    override fun onRemoveClicked(value: ActiveFriendsItem) {
        val mainLst = viewModel.frindsInStreak
        mainLst.find { it.userId == value.userId }?.isDeleted = true
        val lst = mainLst.filter { !it.isDeleted }
        val adapterItems = mutableListOf<AddedFriendEditStreakItemVM>()
        lst.forEach {
            adapterItems.add(AddedFriendEditStreakItemVM(it!!,this))
        }
        adapter.items = adapterItems
        adapter.notifyDataSetChanged()
    }
}