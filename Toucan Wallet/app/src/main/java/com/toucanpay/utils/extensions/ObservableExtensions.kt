package com.toucanpay.utils.extensions

import io.reactivex.Observable

fun <T> T.asObservable() = Observable.just(this)
