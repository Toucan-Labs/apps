package com.toucanpay.utils

import com.toucanpay.ui.base.mvi.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DefaultSchedulersProvider private constructor(): SchedulersProvider {

    override fun subscriptionScheduler(): Scheduler = Schedulers.io()

    override fun observableScheduler(): Scheduler = AndroidSchedulers.mainThread()

    companion object {
        val instance = DefaultSchedulersProvider()
    }
}