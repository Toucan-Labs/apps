package com.toucanwalletdemo.di

import com.toucanwalletdemo.data.provider.MessagesProvider
import com.toucanwalletdemo.data.provider.MessagesThreadProvider
import org.koin.dsl.module.module

val providersModule = module {

    factory { MessagesThreadProvider(get()) }
    factory { MessagesProvider(get()) }
}