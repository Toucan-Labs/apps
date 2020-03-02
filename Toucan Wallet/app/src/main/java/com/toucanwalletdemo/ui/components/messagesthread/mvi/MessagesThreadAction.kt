package com.toucanwalletdemo.ui.components.messagesthread.mvi

import com.toucanwalletdemo.data.models.Message
import com.toucanwalletdemo.ui.base.mvi.MviAction
import com.toucanwalletdemo.ui.model.NewMessageSendData

sealed class MessagesThreadAction: MviAction {

    data class GetMessagesAction(val messages: List<Message>?): MessagesThreadAction()

    data class MessageSendAction(val messageData: NewMessageSendData): MessagesThreadAction()
}