package com.streakify.android.di.module

import com.streakify.android.view.home.launcher.SplashFragment
import com.streakify.android.view.home.onboarding.login.LoginFragment
import com.streakify.android.view.home.onboarding.otp.OtpFragment
import com.streakify.android.view.home.streaks.streaklist.StreakListFragment
import com.streakify.android.view.home.test.TestFragment
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

    @ContributesAndroidInjector
    abstract fun contributeTestFragment(): TestFragment

    @ContributesAndroidInjector
    abstract fun contributeStreakListFragment(): StreakListFragment
}