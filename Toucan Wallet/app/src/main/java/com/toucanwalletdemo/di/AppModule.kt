package com.toucanwalletdemo.di

import com.toucanwalletdemo.ui.base.mvi.SchedulersProvider
import com.toucanwalletdemo.utils.DefaultSchedulersProvider
import org.koin.dsl.module.module

val appModule = module {

    single<SchedulersProvider> { DefaultSchedulersProvider.instance }
}