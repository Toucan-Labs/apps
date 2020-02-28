package com.toucanpay.di

import com.toucanpay.ui.base.mvi.SchedulersProvider
import com.toucanpay.utils.DefaultSchedulersProvider
import org.koin.dsl.module.module

val appModule = module {

    single<SchedulersProvider> { DefaultSchedulersProvider.instance }
}