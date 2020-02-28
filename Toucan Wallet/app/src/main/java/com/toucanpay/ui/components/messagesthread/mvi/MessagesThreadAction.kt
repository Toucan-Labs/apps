package com.toucanpay.ui.components.messagesthread.mvi

import com.toucanpay.data.models.Message
import com.toucanpay.ui.base.mvi.MviAction
import com.toucanpay.ui.model.NewMessageSendData

sealed class MessagesThreadAction: MviAction {

    data class GetMessagesAction(val messages: List<Message>?): MessagesThreadAction()

    data class MessageSendAction(val messageData: NewMessageSendData): MessagesThreadAction()
}