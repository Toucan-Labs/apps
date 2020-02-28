package com.toucanpay.ui.components.messages

import androidx.lifecycle.Lifecycle
import com.toucanpay.data.provider.MessagesProvider
import com.toucanpay.ui.base.mvi.MviViewModel
import com.toucanpay.ui.components.messages.mvi.MessagesAction
import com.toucanpay.ui.components.messages.mvi.MessagesActionProcessor
import com.toucanpay.ui.components.messages.mvi.MessagesResult
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class MessagesViewModel: MviViewModel<MessagesAction, MessagesResult, MessagesViewState>(
    MessagesActionProcessor(),
    MessagesViewState.default()
), KoinComponent {

    private val messageProvider: MessagesProvider by inject()

    override fun initialAction(): MessagesAction? {
        messageProvider.onMessagesUpdated = {
            accept(MessagesAction.LoadMessages(it))
        }
        return null
    }

    override fun onLifecycleAttached(lifecycle: Lifecycle) =
        messageProvider.init(lifecycle)
}