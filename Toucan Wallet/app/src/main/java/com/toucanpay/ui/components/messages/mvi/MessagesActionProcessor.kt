package com.toucanpay.ui.components.messages.mvi

import com.toucanpay.ui.base.mvi.*
import com.toucanpay.ui.components.messages.mvi.MessagesAction.LoadMessages
import com.toucanpay.ui.components.messages.mvi.MessagesResult.*
import io.reactivex.Observable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class MessagesActionProcessor: MviActionsProcessor<MessagesAction, MessagesResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()

    override fun getActionProcessors(shared: Observable<MessagesAction>) = listOf(
        shared.connect(loadMessagesActionProcessor)
    )

    private val loadMessagesActionProcessor =
        createActionProcessor<LoadMessages, MessagesResult>(
            schedulersProvider,
            { InFlight },
            { Error(it.localizedMessage) }
        ) {
            onNextSafe(Success(it.messages))
            onCompleteSafe()
        }
}