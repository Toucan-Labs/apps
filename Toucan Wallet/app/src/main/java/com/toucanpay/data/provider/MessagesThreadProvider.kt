package com.toucanpay.data.provider

import com.toucanpay.data.models.Message
import com.toucanpay.data.provider.base.DisposableProvider
import com.toucanpay.data.repositories.MessagesRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MessagesThreadProvider(
    private val messagesRepository: MessagesRepository
): DisposableProvider() {

    lateinit var onMessagesUpdated: (List<Message>) -> Unit
    var username: String? = null

    override fun createDisposable(): Disposable? =
        Observable.interval(0, 2, TimeUnit.SECONDS)
            .flatMap { messagesRepository.getMessagesThread(username) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onMessagesUpdated(it)
            }, {
                it.printStackTrace()
            })
}