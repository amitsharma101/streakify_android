package com.streakify.android.view.home.onboarding.login

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.databinding.LoginLayoutBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.view.activity.MainActivity
import com.streakify.android.view.home.onboarding.otp.OtpFragment
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class LoginFragment : BaseFragment<LoginLayoutBinding, LoginVM>() {

    companion object{
        private const val TAG = "LoginFragment"
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

        binding.requestOtpBtn.setOnClickListener {
            if(viewModel.validData()){
                viewModel.checkNetwork()

                val bundle = bundleOf(
                    OtpFragment.PHONE_NUMBER to viewModel.phoneField.get()
                )
                findNavController().navigate(
                    R.id.action_loginFragment_to_otpFragment,bundle
                )
            }
            }
        }
}