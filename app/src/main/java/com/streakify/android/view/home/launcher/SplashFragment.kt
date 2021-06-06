package com.streakify.android.view.home.launcher

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashVM>() {

    override fun setLayout() = R.layout.fragment_splash

    override fun setViewModel() = SplashVM::class.java


    /** Bind View with ViewModel */
    override fun bindViews() {
        binding.viewModel = viewModel

        /* Hide ActionBar */
//        (activity as MainActivity).hideActionBar()

//        viewModel.checkUserLoggedIn()

        /* Set Observers to capture actions */
//        bindObservers()


        if (FirebaseAuth.getInstance().currentUser != null) {
            findNavController().navigate(R.id.action_splashFragment_to_streakListFragment)
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
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