package com.example.streakify.di.module

import com.example.streakify.view.home.launcher.SplashFragment
import com.example.streakify.view.home.onboarding.login.LoginFragment
import com.example.streakify.view.home.onboarding.otp.OtpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributeOtpFragment(): OtpFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment
}