package com.toucanpay.ui.components.messagesthread

import androidx.lifecycle.Lifecycle
import com.toucanpay.data.provider.MessagesThreadProvider
import com.toucanpay.ui.base.mvi.MviViewModel
import com.toucanpay.ui.components.messagesthread.mvi.MessagesThreadAction
import com.toucanpay.ui.components.messagesthread.mvi.MessagesThreadActionProcessor
import com.toucanpay.ui.components.messagesthread.mvi.MessagesThreadResult
import com.toucanpay.ui.model.NewMessageSendData
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class MessagesThreadViewModel: MviViewModel<MessagesThreadAction, MessagesThreadResult, MessagesThreadViewState>(
    MessagesThreadActionProcessor(),
    MessagesThreadViewState.default()
), KoinComponent {

    private val messageProvider: MessagesThreadProvider by inject()

    override fun initialAction(): MessagesThreadAction? {
        messageProvider.onMessagesUpdated = {
            accept(MessagesThreadAction.GetMessagesAction(it))
        }
        return null
    }

    override fun onLifecycleAttached(lifecycle: Lifecycle) =
        messageProvider.init(lifecycle)

    fun initFilterMessages(accountUsername: String) =
        messageProvider.also { it.username = accountUsername }

    fun sendMessage(toAccount: String, message: String) =
        accept(MessagesThreadAction.MessageSendAction(NewMessageSendData(toAccount, message)))
}