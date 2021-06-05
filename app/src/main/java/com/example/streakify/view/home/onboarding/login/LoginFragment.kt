package com.example.streakify.view.home.onboarding.login

import android.app.Activity
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.streakify.R
import com.example.streakify.base.BaseFragment
import com.example.streakify.databinding.LoginLayoutBinding
import com.example.streakify.di.provider.ResourceProvider
import com.example.streakify.view.activity.MainActivity
import com.example.streakify.view.home.onboarding.otp.OtpFragment
import javax.inject.Inject

class LoginFragment : BaseFragment<LoginLayoutBinding, LoginVM>() {

    companion object{
        const val REQUEST_PHONE_HINT = 100
    }
    @Inject
    lateinit var resourceProvider: ResourceProvider

    /**
     * @return Layout Resource Such as R.layout.login_layout
     * */

    override fun setLayout() = R.layout.login_layout

    /**
     * @return Class which extends {@link ViewModel}
     * */

    override fun setViewModel() = LoginVM::class.java

    /** Bind View with ViewModel */
    override fun bindViews() {

        binding.viewModel = viewModel

        /* Hide ActionBar */
//        (activity as MainActivity).hideActionBar()

        binding.ccp.isClickable = false
        /* Set Observers to capture actions */
        bindObservers()
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {

        /* Navigate to OTP Screen */
        viewModel.mDestinationForward.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandled()?.let {

                val bundle = bundleOf(
                    OtpFragment.PHONE_NUMBER to viewModel.phoneField.get()
                )
                findNavController().navigate(
                    R.id.action_loginFragment_to_otpFragment,bundle
                )
            }
        })

    }
}