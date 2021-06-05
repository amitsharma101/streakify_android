package com.example.streakify.di.module

import com.example.streakify.view.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingsModule {

    @ContributesAndroidInjector(
        modules = [MainFragmentBuilderModule::class]
    )
    abstract fun mainActivity(): MainActivity
}