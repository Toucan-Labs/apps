package com.toucanpay.ui.components.messagesthread.mvi

import com.toucanpay.data.models.Message
import com.toucanpay.ui.base.mvi.MviResult

sealed class MessagesThreadResult: MviResult {

    object InFlight: MessagesThreadResult()

    data class Success(val messages: List<Message>?, val uniqueMessages: List<Message>?): MessagesThreadResult()

    object MessageSendSuccess: MessagesThreadResult()

    data class Error(val errorMessage: String): MessagesThreadResult()
}