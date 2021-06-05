package com.example.streakify.view.home.onboarding.otp

import android.os.Bundle
import com.example.streakify.R
import com.example.streakify.base.BaseFragment
import com.example.streakify.databinding.OtpLayoutBinding
import com.example.streakify.di.provider.ResourceProvider
import com.example.streakify.utils.extensions.Extensions.Companion.addPropertyChangedCallback
import com.example.streakify.view.activity.MainActivity

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