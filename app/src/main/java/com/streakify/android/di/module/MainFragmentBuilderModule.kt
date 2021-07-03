package com.streakify.android.di.module

import com.streakify.android.view.home.friends.addfriend.AddFriendFragment
import com.streakify.android.view.home.friends.firendrequests.FriendRequestFragment
import com.streakify.android.view.home.friends.firendslist.FriendsListFragment
import com.streakify.android.view.home.launcher.SplashFragment
import com.streakify.android.view.home.onboarding.login.LoginFragment
import com.streakify.android.view.home.onboarding.otp.OtpFragment
import com.streakify.android.view.home.profile.EditProfileFragment
import com.streakify.android.view.home.profile.ProfileDetailFragment
import com.streakify.android.view.home.streaks.editstreak.EditStreakFragment
import com.streakify.android.view.home.streaks.editstreak.data.AddFriendBottomSheet
import com.streakify.android.view.home.streaks.streakdetail.StreakDetailFragment
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

    @ContributesAndroidInjector
    abstract fun contributeProfileDetailFragment(): ProfileDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeAddFriendFragment(): AddFriendFragment

    @ContributesAndroidInjector
    abstract fun contributeFriendsListFragment(): FriendsListFragment

    @ContributesAndroidInjector
    abstract fun contributeStreakDetailFragment(): StreakDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeFriendRequestFragment(): FriendRequestFragment

    @ContributesAndroidInjector
    abstract fun contributeAddFriendBottomSheet(): AddFriendBottomSheet
}