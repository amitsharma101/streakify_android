package com.streakify.android.view.home.streaks.editstreak

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.streakify.android.R
import com.streakify.android.application.AppConstants
import com.streakify.android.base.BaseFragment
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.EditStreakLayoutBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.streaks.StreaksEvent
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
    }

    override fun handleEvent(event: Event) {
        when(event){
            is StreaksEvent.DeifiniteClickedEvent -> {
                onDefiniteClicked()
            }
            is StreaksEvent.IndeifiniteClickedEvent -> {
                onIndefiniteClicked()
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
}