package com.streakify.android.view.home.onboarding.login

import android.util.Log
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.*
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.databinding.LoginLayoutBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.Logger.log
import com.streakify.android.view.home.onboarding.otp.OtpFragment
import java.util.logging.Logger
import java.util.logging.Logger.*
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

                Log.i("CC",binding.ccp.selectedCountryCode)

                val bundle = bundleOf(
                    OtpFragment.PHONE_NUMBER to viewModel.phoneField.get(),
                    OtpFragment.CC to binding.ccp.selectedCountryCode,
                )
                findNavController().navigate(
                    R.id.action_loginFragment_to_otpFragment,bundle
                )
            }
            }
        }
}