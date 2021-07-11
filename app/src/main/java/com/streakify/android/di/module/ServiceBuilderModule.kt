package com.streakify.android.di.module

import com.streakify.android.application.FcmMessageService
import dagger.android.ContributesAndroidInjector
import dagger.Module


@Module
abstract class ServiceBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeFcmMessageService(): FcmMessageService
}