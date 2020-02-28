package com.toucanpay.di

import com.toucanpay.utils.logout.LogoutScheduler
import com.toucanpay.utils.qrcode.QrCodeManager
import org.koin.dsl.module.module

val utilsModule = module {

    factory { QrCodeManager() }
    factory { LogoutScheduler(get()) }
}