package com.toucanpay.ui.base.mvi

import io.reactivex.Scheduler

interface SchedulersProvider {

    fun subscriptionScheduler(): Scheduler

    fun observableScheduler(): Scheduler
}