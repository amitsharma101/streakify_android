package com.streakify.android.di.module


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.streakify.android.di.annotation.ViewModelKey
import com.streakify.android.di.provider.ViewModelProviderFactory
import com.streakify.android.view.activity.MainActivityViewModel
import com.streakify.android.view.home.launcher.SplashVM
import com.streakify.android.view.home.onboarding.login.LoginVM
import com.streakify.android.view.home.onboarding.otp.OtpVM

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton


@Module
abstract class ViewModelsModule {

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun viewModelProviderFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory =
            factory
    }

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainActivityViewModel): ViewModel

    // Splash
    @Binds
    @IntoMap
    @ViewModelKey(SplashVM::class)
    abstract fun bindSplashViewModel(viewModel: SplashVM): ViewModel

    // Otp
    @Binds
    @IntoMap
    @ViewModelKey(OtpVM::class)
    abstract fun bindOtpViewModel(viewModel: OtpVM): ViewModel

    // Login
    @Binds
    @IntoMap
    @ViewModelKey(LoginVM::class)
    abstract fun bindLoginViewModel(viewModel: LoginVM): ViewModel
}