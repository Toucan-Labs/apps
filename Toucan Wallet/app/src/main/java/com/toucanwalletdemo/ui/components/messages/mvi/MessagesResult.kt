package com.toucanwalletdemo.ui.components.messages.mvi

import com.toucanwalletdemo.data.models.Message
import com.toucanwalletdemo.ui.base.mvi.MviResult

sealed class MessagesResult: MviResult {

    object InFlight: MessagesResult()

    data class Success(val messages: List<Message>): MessagesResult()

    data class Error(val errorMessage: String): MessagesResult()
}