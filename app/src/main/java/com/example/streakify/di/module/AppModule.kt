package com.example.streakify.di.module

import android.content.Context
import com.example.streakify.application.App
import com.example.streakify.utils.LocalPreferences
import com.example.streakify.utils.network.NetworkLiveData
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object AppModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideContext(app: App): Context = app.applicationContext


    @Provides
    @Singleton
    @JvmStatic
    fun storePreferences(context: Context): LocalPreferences = LocalPreferences(context)

    @Provides
    @Singleton
    @JvmStatic
    fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .setPrettyPrinting()
            .setLenient()
            .serializeNulls()
            .create()
    }

    @Provides
    @JvmStatic
    @Singleton
    fun providesConnectivityLiveData(app: App) =
        NetworkLiveData(app)
}