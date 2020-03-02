package com.toucanwalletdemo.ui.components.newmessage

import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.newmessage.mvi.NewMessageAction
import com.toucanwalletdemo.ui.components.newmessage.mvi.NewMessageAction.MessageSendAction
import com.toucanwalletdemo.ui.components.newmessage.mvi.NewMessageActionProcessor
import com.toucanwalletdemo.ui.components.newmessage.mvi.NewMessageResult

class NewMessageViewModel: MviViewModel<NewMessageAction, NewMessageResult, NewMessageViewState>(
    NewMessageActionProcessor(),
    NewMessageViewState.default()
) {

    fun onMessageSendButton(targetUsername: String) {
        accept(MessageSendAction(targetUsername))
    }

    override fun initialAction(): NewMessageAction? = null
}