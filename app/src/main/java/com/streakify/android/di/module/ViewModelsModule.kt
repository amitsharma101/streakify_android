package com.streakify.android.di.module


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.streakify.android.di.annotation.ViewModelKey
import com.streakify.android.di.provider.ViewModelProviderFactory
import com.streakify.android.view.activity.MainActivityViewModel
import com.streakify.android.view.home.friends.addfriend.AddFriendVM
import com.streakify.android.view.home.friends.firendslist.FriendsListVM
import com.streakify.android.view.home.launcher.SplashVM
import com.streakify.android.view.home.onboarding.login.LoginVM
import com.streakify.android.view.home.onboarding.otp.OtpVM
import com.streakify.android.view.home.profile.EditProfileVM
import com.streakify.android.view.home.streaks.editstreak.EditStreakVM
import com.streakify.android.view.home.streaks.streakdetail.StreakDetailVM
import com.streakify.android.view.home.streaks.streaklist.StreakListVM

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

    // StreakList - Home
    @Binds
    @IntoMap
    @ViewModelKey(StreakListVM::class)
    abstract fun bindStreakListViewModel(viewModel: StreakListVM): ViewModel

    // StreakList - Home
    @Binds
    @IntoMap
    @ViewModelKey(EditStreakVM::class)
    abstract fun bindEditStreakViewModel(viewModel: EditStreakVM): ViewModel

    // Profile - Home
    @Binds
    @IntoMap
    @ViewModelKey(EditProfileVM::class)
    abstract fun bindEditProfileViewModel(viewModel: EditProfileVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddFriendVM::class)
    abstract fun bindAddFriendViewModel(viewModel: AddFriendVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FriendsListVM::class)
    abstract fun bindFriendsListViewModel(viewModel: FriendsListVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StreakDetailVM::class)
    abstract fun bindStreakDetailVM(viewModel: StreakDetailVM): ViewModel
}