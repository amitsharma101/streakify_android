package com.example.streakify.di.component

import com.example.streakify.application.App
import com.example.streakify.di.module.*
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