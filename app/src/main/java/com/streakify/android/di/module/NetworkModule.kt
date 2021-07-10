package com.streakify.android.di.module


import android.content.Context
import com.streakify.android.networkcall.ApiConstant
import com.google.gson.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.streakify.android.BuildConfig
import com.streakify.android.commonrepo.CommonApiServices
import com.streakify.android.networkcall.NetworkInterceptor
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.view.home.onboarding.repo.AuthApiServices

@Module
object NetworkModule {
    @Provides
    @Singleton
    @JvmStatic
    fun okHttpClient(context : Context, localPreferences: LocalPreferences): OkHttpClient {

        val spec = ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
            .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA
            )
            .build()
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(NetworkInterceptor(localPreferences))
            .addInterceptor(ChuckInterceptor(context))
            .addInterceptor(getHttpLoggingInterceptor())
            .build()

        val connectionSpecs = okHttpClient.connectionSpecs
        connectionSpecs.map { Collections.singletonList(spec) }
        return okHttpClient

    }

    @Provides
    @Singleton
    @JvmStatic
    fun retrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    }

    private fun getBaseUrl(): String {
        return if (BuildConfig.DEBUG) {
            ApiConstant.BASE_URL
        } else {
            ApiConstant.BASE_PROD_URL
        }
    }

    @Provides
    @JvmStatic
    fun webService(retrofit: Retrofit): CommonApiServices =
        retrofit.create(CommonApiServices::class.java)


    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @JvmStatic
    fun authServices(retrofit: Retrofit): AuthApiServices =
        retrofit.create(AuthApiServices::class.java)
}