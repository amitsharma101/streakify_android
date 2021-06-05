package com.example.streakify.view.home.launcher

import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.streakify.R
import com.example.streakify.base.BaseFragment
import com.example.streakify.databinding.FragmentSplashBinding
import com.example.streakify.view.activity.MainActivity

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashVM>() {

    override fun setLayout() = R.layout.fragment_splash

    override fun setViewModel() = SplashVM::class.java


    /** Bind View with ViewModel */
    override fun bindViews() {
        binding.viewModel = viewModel

        /* Hide ActionBar */
//        (activity as MainActivity).hideActionBar()

        viewModel.checkUserLoggedIn()

        /* Set Observers to capture actions */
        bindObservers()

    }

    private fun bindObservers() {
        viewModel.mUserAccountLD.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandled()?.let {
                if (it) {
//                    bindSyncLevelObserver()
                } else
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        })
    }
}