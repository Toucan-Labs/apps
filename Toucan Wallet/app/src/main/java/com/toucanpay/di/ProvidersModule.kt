package com.toucanpay.di

import com.toucanpay.data.provider.MessagesProvider
import com.toucanpay.data.provider.MessagesThreadProvider
import org.koin.dsl.module.module

val providersModule = module {

    factory { MessagesThreadProvider(get()) }
    factory { MessagesProvider(get()) }
}