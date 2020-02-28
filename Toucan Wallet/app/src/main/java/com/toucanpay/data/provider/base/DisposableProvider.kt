package com.toucanpay.data.provider.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposableProvider: LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()

    fun init(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        createDisposable()?.let { disposable ->
            compositeDisposable.add(disposable)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        compositeDisposable.clear()
    }

    abstract fun createDisposable(): Disposable?
}