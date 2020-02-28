package com.toucanpay.ui.components.messages.mvi

import com.toucanpay.data.models.Message
import com.toucanpay.ui.base.mvi.MviResult

sealed class MessagesResult: MviResult {

    object InFlight: MessagesResult()

    data class Success(val messages: List<Message>): MessagesResult()

    data class Error(val errorMessage: String): MessagesResult()
}