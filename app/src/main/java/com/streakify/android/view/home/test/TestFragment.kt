package com.streakify.android.view.home.test

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.databinding.FragmentTestBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.view.home.onboarding.login.LoginVM
import com.streakify.android.view.home.onboarding.otp.OtpFragment
import javax.inject.Inject

class TestFragment : BaseFragment<FragmentTestBinding, LoginVM>() {

    companion object{
        private const val TAG = "TestFragment"
    }
    @Inject
    lateinit var resourceProvider: ResourceProvider

    /**
     * @return Layout Resource Such as R.layout.login_layout
     * */

    override fun setLayout() = R.layout.fragment_test

    /**
     * @return Class which extends {@link ViewModel}
     * */

    override fun setViewModel() = LoginVM::class.java

    /** Bind View with ViewModel */

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

    }

    override fun bindViews() {

//        binding.viewModel = viewModel

        /* Hide ActionBar */
//        (activity as MainActivity).hideActionBar()

        /* Set Observers to capture actions */
        bindObservers()


    }

    /** Set Observers to capture actions */
    private fun bindObservers() {

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_streakListFragment_to_splashFragment)
        }

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