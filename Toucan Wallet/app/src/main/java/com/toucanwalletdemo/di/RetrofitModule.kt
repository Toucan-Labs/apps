package com.toucanwalletdemo.di

import com.google.gson.GsonBuilder
import com.toucanwalletdemo.BuildConfig
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.remote.backend.AuthenticationInterceptor
import com.toucanwalletdemo.data.remote.backend.ToucanBackend
import com.toucanwalletdemo.data.repositories.UserRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {

    factory { get<Retrofit>().create(ToucanBackend::class.java) }

    single {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.toucan_backend_url))
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(get(AUTHENTICATION_INTERCEPTOR_NAME))
            .build()
    }

    single<Interceptor>(AUTHENTICATION_INTERCEPTOR_NAME) {
        AuthenticationInterceptor(lazy { get<UserRepository>() })
    }
}

private const val AUTHENTICATION_INTERCEPTOR_NAME = "authenticationInterceptor"