package com.streakify.android.view.home.streaks.editstreak.data

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.compose.ui.text.toLowerCase
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.streakify.android.R
import com.streakify.android.base.adapter.CommonAdapter
import com.streakify.android.base.adapter.ItemClickListener
import com.streakify.android.databinding.AddFriendBottomsheetBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.home.friends.firendslist.data.ActiveFriendsItem
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.edit_streak_layout.*
import javax.inject.Inject


const val SEARCH = "search"
const val HSN_SAC_Type = "hsn_sac_type"

class AddFriendBottomSheet(val friends: List<ActiveFriendsItem?>?,val listener:AddFriendBSInterface) : BottomSheetDialogFragment(),
    ItemClickListener<AddFriendBSItemVM> {

    private lateinit var adapter: CommonAdapter<AddFriendBSItemVM>
    private var adapterList: MutableList<AddFriendBSItemVM> = ArrayList()

    @Inject
    lateinit var resourceProvider: ResourceProvider

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: AddFriendBottomSheetVM
    lateinit var binding: AddFriendBottomsheetBinding

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.add_friend_bottomsheet,
                container,
                false
            )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddFriendBottomSheetVM::class.java)
        binding.viewModel = viewModel
        initView()
    }

    private fun initView() {
        val friendsForBS = mutableListOf<AddFriendBSItemVM>()
        friends?.forEach {
            friendsForBS.add(AddFriendBSItemVM(it!!))
        }
        adapterList = friendsForBS
        adapter = CommonAdapter(adapterList, this)
        binding.recyclerView.adapter = adapter

        searchViewClickListener(binding.simpleSearchView)
        binding.crossIcon.setOnClickListener { v ->
            dialog?.dismiss()
        }
    }

    fun searchViewClickListener(simpleSearchView: SearchView) {
        simpleSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {

                    val newList = friends?.filter { it?.name?.contains(newText,true) == true
                            || it?.mobileNumber?.contains(newText,true) == true
                    }
                    val nl = mutableListOf<AddFriendBSItemVM>()
                    newList?.forEach {
                        nl.add(AddFriendBSItemVM(it!!))
                    }
                    adapter.items = nl
                    adapter.notifyDataSetChanged()
                }
                return false
            }
        })
    }



    override fun onItemClick(value: AddFriendBSItemVM) {
        dialog?.dismiss()
        listener.onFriendClicked(value)
    }
}

interface AddFriendBSInterface{
    fun onFriendClicked(value:AddFriendBSItemVM)
}
