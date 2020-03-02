package com.toucanwalletdemo.ui.components.messagesthread.mvi

import com.toucanwalletdemo.data.models.MessageData
import com.toucanwalletdemo.data.repositories.MessagesRepository
import com.toucanwalletdemo.ui.base.mvi.*
import com.toucanwalletdemo.ui.components.messagesthread.mvi.MessagesThreadAction.GetMessagesAction
import com.toucanwalletdemo.ui.components.messagesthread.mvi.MessagesThreadAction.MessageSendAction
import com.toucanwalletdemo.ui.components.messagesthread.mvi.MessagesThreadResult.*
import com.toucanwalletdemo.utils.extensions.toFormattedDayString
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.util.*

class MessagesThreadActionProcessor: MviActionsProcessor<MessagesThreadAction, MessagesThreadResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val messagesRepository: MessagesRepository by inject()

    override fun getActionProcessors(shared: Observable<MessagesThreadAction>) = listOf(
        shared.connect(loadMessagesActionProcessor),
        shared.connect(sendMessageActionProcessor)
    )

    private val loadMessagesActionProcessor =
        createActionProcessor<GetMessagesAction, MessagesThreadResult>(
            schedulersProvider,
            { InFlight },
            { Error(it.localizedMessage) }) {

            val uniqueDates = it.messages?.reversed()?.distinctBy { time ->
                Date(time.timestamp * 1000).toFormattedDayString()
            }

            onNextSafe(Success(it.messages, uniqueDates))
            onCompleteSafe()
        }

    private val sendMessageActionProcessor = ObservableTransformer<MessageSendAction, MessagesThreadResult> { action ->
        action.flatMap { data ->
            messagesRepository.sendMessage(MessageData(data.messageData.toAccount, data.messageData.message))
                .subscribeOn(schedulersProvider.subscriptionScheduler())
                .observeOn(schedulersProvider.observableScheduler())
                .map {
                    if (it.error != null) {
                        Error(it.error)
                    } else {
                        MessageSendSuccess
                    }
                }
                .cast(MessagesThreadResult::class.java)
                .startWith(InFlight)
                .onErrorReturn { Error(it.localizedMessage) }
        }
    }
}