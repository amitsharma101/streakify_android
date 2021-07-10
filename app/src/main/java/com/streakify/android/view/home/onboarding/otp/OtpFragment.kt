package com.streakify.android.view.home.onboarding.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.streakify.android.R
import com.streakify.android.base.BaseFragment
import com.streakify.android.databinding.OtpLayoutBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.Logger
import com.streakify.android.utils.extensions.Extensions.Companion.addPropertyChangedCallback
import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.activity.MainActivity
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class OtpFragment : BaseFragment<OtpLayoutBinding, OtpVM>()
{

    private lateinit var  mAuth: FirebaseAuth
    private lateinit var mCallbacks: OnVerificationStateChangedCallbacks
    private lateinit var verificationId: String
    private lateinit var mResendToken: ForceResendingToken

    var cTimer: CountDownTimer? = null

    /** KEYS */
    companion object {
        val PHONE_NUMBER = "PHONE_NUMBER"
        val CC = "CC"
    }

    private var countryCode: String? = null
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

        initFirebaseAuth()
    }

    private fun initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance()
        mCallbacks = object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.i("CALLBACK","onVerificationCompleted Auto")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                    viewModel.eventListener.showSnackMessage(getString(R.string.error_invalid_phone_number))
                } else if (e is FirebaseTooManyRequestsException) {
                    viewModel.eventListener.showSnackMessage(getString(R.string.unknown_error))
                } else if (e is FirebaseNetworkException) {
                    viewModel.eventListener.showSnackMessage(getString(R.string.could_not_connect_to_the_internet_please_check_your_network))
                } else {
                    viewModel.eventListener.showSnackMessage(e.message)
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: ForceResendingToken
            ) {
                Log.i("CALLBACK","Code Sent")

                startTimer()

                this@OtpFragment.verificationId = verificationId
                mResendToken = token
            }
        }
    }

    /** Bind View with ViewModel */
    override fun bindViews() {
        binding.viewModel = viewModel

        /* Hide ActionBar */
//        (activity as MainActivity).hideActionBar()
        /* Get Arguments */
        countryCode = arguments?.getString(CC)
        phone = arguments?.getString(PHONE_NUMBER)

        viewModel.phoneNumber = arguments?.getString(PHONE_NUMBER)!!
        viewModel.countryCode = "+"+arguments?.getString(CC)

        /* Set Phone value to ViewModel */
        viewModel.phoneField.set(phone)

        bindObservers()

        Logger.log("+$countryCode$phone")

        startPhoneNumberVerification("+$countryCode$phone")
    }

    fun startTimer() {
        viewModel.resendVisibility.set(View.GONE)
        viewModel.timerVisibility.set(View.VISIBLE)

        cTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                viewModel.timerString.set((millisUntilFinished / 1000).toString())
            }
            override fun onFinish() {
                viewModel.resendVisibility.set(View.VISIBLE)
                viewModel.timerVisibility.set(View.GONE)
            }
        }
        (cTimer as CountDownTimer).start()
    }

    //cancel timer
    fun cancelTimer() {
        if (cTimer != null) cTimer!!.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cancelTimer()
    }

    private fun bindObservers() {
        binding.resendotp.setOnClickListener {
            if(this::verificationId.isInitialized && verificationId.isNotEmpty()) {

                startTimer()

                resendVerificationCode("+$countryCode$phone", mResendToken)
            }
            else{
                initFirebaseAuth()
            }
        }

        binding.submitBtn.setOnClickListener {
            verifyOtpManually(verificationId)
        }

        viewModel.signedInSuccess.addPropertyChangedCallback {

        }
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: ForceResendingToken
    ) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            activity as MainActivity,
            mCallbacks,
            token
        )
        viewModel.eventListener.showSnackMessage(getString(R.string.msg_otp_resent))
    }

    fun verifyOtpManually(verificationId: String?) {
        if (verificationId == null) {
            return
        }
        viewModel.eventListener.showLoading()
        try {
            val credential = PhoneAuthProvider.getCredential(verificationId, binding.otpView.text.toString())
            signInWithPhoneAuthCredential(credential)
        } catch (e: Exception) {
            viewModel.eventListener.showMessageDialog(e.message)
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            activity as MainActivity,
            mCallbacks
        )
    }

    override fun handleEvent(event: Event) {
        when(event) {
            is OtpEvent.LoginSuccessEvent -> {
                findNavController().navigate(
                    R.id.action_otpFragment_to_splashFragment
                )
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity as MainActivity) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser = task.result.user!!
                    user.getIdToken(false).addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            return@addOnCompleteListener
                        }

                        val firebaseToken = task.result.token

                        if (firebaseToken != null) {
                            viewModel.fireBaseToken = firebaseToken
                            viewModel.getToken(firebaseToken)
                        }
                    }
                } else {
                    // Sign in failed, display a message and update the UI
                    when (task.exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            //showSnackBar(getString(R.string.invalid_otp));
                            viewModel.eventListener.showMessageDialog("Invalid OTP")
                        }
                        is FirebaseNetworkException -> {
                            viewModel.eventListener.showSnackMessage(getString(R.string.could_not_connect_to_the_internet_please_check_your_network))
                        }
                        else -> {
                            viewModel.eventListener.showSnackMessage(getString(R.string.unknown_error))
                        }
                    }
                }
            }
    }
}