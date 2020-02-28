package com.toucanpay.ui.components.newmessage

import com.toucanpay.ui.base.mvi.MviViewModel
import com.toucanpay.ui.components.newmessage.mvi.NewMessageAction
import com.toucanpay.ui.components.newmessage.mvi.NewMessageAction.MessageSendAction
import com.toucanpay.ui.components.newmessage.mvi.NewMessageActionProcessor
import com.toucanpay.ui.components.newmessage.mvi.NewMessageResult

class NewMessageViewModel: MviViewModel<NewMessageAction, NewMessageResult, NewMessageViewState>(
    NewMessageActionProcessor(),
    NewMessageViewState.default()
) {

    fun onMessageSendButton(targetUsername: String) {
        accept(MessageSendAction(targetUsername))
    }

    override fun initialAction(): NewMessageAction? = null
}