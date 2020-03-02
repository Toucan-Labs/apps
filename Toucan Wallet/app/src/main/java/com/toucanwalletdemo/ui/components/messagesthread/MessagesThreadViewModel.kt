package com.toucanwalletdemo.ui.components.messagesthread

import androidx.lifecycle.Lifecycle
import com.toucanwalletdemo.data.provider.MessagesThreadProvider
import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.messagesthread.mvi.MessagesThreadAction
import com.toucanwalletdemo.ui.components.messagesthread.mvi.MessagesThreadActionProcessor
import com.toucanwalletdemo.ui.components.messagesthread.mvi.MessagesThreadResult
import com.toucanwalletdemo.ui.model.NewMessageSendData
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