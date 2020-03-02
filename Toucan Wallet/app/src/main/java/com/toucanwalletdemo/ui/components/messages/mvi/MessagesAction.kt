package com.toucanwalletdemo.ui.components.messages.mvi

import com.toucanwalletdemo.data.models.Message
import com.toucanwalletdemo.ui.base.mvi.MviAction

sealed class MessagesAction: MviAction {

    data class LoadMessages(val messages: List<Message>): MessagesAction()
}