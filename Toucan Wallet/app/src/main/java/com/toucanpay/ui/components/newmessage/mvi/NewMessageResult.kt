package com.toucanpay.ui.components.newmessage.mvi

import com.toucanpay.ui.base.mvi.MviResult

sealed class NewMessageResult: MviResult {

    object InFlight: NewMessageResult()

    data class MoveToMessagesThread(val username: String): NewMessageResult()

    data class Error(val errorMessage: String): NewMessageResult()
}