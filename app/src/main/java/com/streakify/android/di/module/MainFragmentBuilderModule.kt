package com.streakify.android.di.module

import com.streakify.android.view.home.launcher.SplashFragment
import com.streakify.android.view.home.onboarding.login.LoginFragment
import com.streakify.android.view.home.onboarding.otp.OtpFragment
import com.streakify.android.view.home.profile.EditProfileFragment
import com.streakify.android.view.home.streaks.editstreak.EditStreakFragment
import com.streakify.android.view.home.streaks.streaklist.StreakListFragment
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
    abstract fun contributeStreakListFragment(): StreakListFragment

    @ContributesAndroidInjector
    abstract fun contributeEditStreakFragment(): EditStreakFragment

    @ContributesAndroidInjector
    abstract fun contributeEditProfileFragment(): EditProfileFragment
}