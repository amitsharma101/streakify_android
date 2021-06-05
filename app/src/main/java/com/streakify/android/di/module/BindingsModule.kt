package com.streakify.android.di.module

import com.streakify.android.view.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingsModule {

    @ContributesAndroidInjector(
        modules = [MainFragmentBuilderModule::class]
    )
    abstract fun mainActivity(): MainActivity
}