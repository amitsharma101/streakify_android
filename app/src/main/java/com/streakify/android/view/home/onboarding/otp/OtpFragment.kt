package com.streakify.android.view.home.onboarding.otp

import android.os.Bundle
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.databinding.OtpLayoutBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.extensions.Extensions.Companion.addPropertyChangedCallback

import java.util.*
import javax.inject.Inject


class OtpFragment : BaseFragment<OtpLayoutBinding, OtpVM>()
{

    /** KEYS */
    companion object {
        val PHONE_NUMBER = "PHONE_NUMBER"
    }

    private var phone: String? = null
    @Inject
    lateinit var resourceProvider: ResourceProvider

    /**
     * @return Layout Resource Such as R.layout.fragment_splash
     * */
    override fun setLayout() = R.layout.otp_layout

    /**
     * @return Class which extends {@link ViewModel}
     * */
    override fun setViewModel() = OtpVM::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /** Bind View with ViewModel */
    override fun bindViews() {
        binding.viewModel = viewModel

        /* Hide ActionBar */
//        (activity as MainActivity).hideActionBar()
        /* Get Arguments */
        phone = arguments?.getString(PHONE_NUMBER)

        /* Set Phone value to ViewModel */
        viewModel.phoneField.set(phone)

        bindObservers()
    }

    private fun bindObservers() {
        viewModel.signedInSuccess.addPropertyChangedCallback {

        }
    }
}