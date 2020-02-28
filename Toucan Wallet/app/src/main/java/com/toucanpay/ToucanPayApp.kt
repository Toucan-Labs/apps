package com.toucanpay

import android.app.Application
import com.toucanpay.di.*
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.android.startKoin

class ToucanPayApp: Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this

        initKoin()
        initRxJavaErrorHandler()
    }

    private fun initKoin() {
        startKoin(this, listOf(appModule, repositoriesModule, utilsModule, retrofitModule, providersModule))
    }

    private fun initRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler { t: Throwable? ->
            if (t is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
            }
        }
    }

    companion object {

        @JvmStatic
        lateinit var instance: ToucanPayApp
            private set
    }
}