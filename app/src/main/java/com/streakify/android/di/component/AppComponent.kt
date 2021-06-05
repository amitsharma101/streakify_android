package com.streakify.android.di.component

import com.streakify.android.application.App
import com.streakify.android.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
//        ServiceBuilderModule::class,
        AppModule::class,
        BindingsModule::class,
        ViewModelsModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }
}