package com.toucanwalletdemo.di

import com.toucanwalletdemo.utils.logout.LogoutScheduler
import com.toucanwalletdemo.utils.qrcode.QrCodeManager
import org.koin.dsl.module.module

val utilsModule = module {

    factory { QrCodeManager() }
    factory { LogoutScheduler(get()) }
}