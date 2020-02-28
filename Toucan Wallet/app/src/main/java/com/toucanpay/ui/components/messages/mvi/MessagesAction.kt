package com.toucanpay.ui.components.messages.mvi

import com.toucanpay.data.models.Message
import com.toucanpay.ui.base.mvi.MviAction

sealed class MessagesAction: MviAction {

    data class LoadMessages(val messages: List<Message>): MessagesAction()
}