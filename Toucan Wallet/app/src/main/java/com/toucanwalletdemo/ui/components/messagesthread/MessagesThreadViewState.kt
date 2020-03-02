package com.toucanwalletdemo.ui.components.messagesthread

import com.toucanwalletdemo.data.models.Message
import com.toucanwalletdemo.ui.base.mvi.MviViewState
import com.toucanwalletdemo.ui.base.mvi.ViewStateEmptyEvent
import com.toucanwalletdemo.ui.base.mvi.ViewStateEvent
import com.toucanwalletdemo.ui.components.messagesthread.mvi.MessagesThreadResult
import com.toucanwalletdemo.ui.components.messagesthread.mvi.MessagesThreadResult.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessagesThreadViewState(
    val inProgress: Boolean,
    val messages: List<Message>?,
    val uniqueMessages: List<Message>?,
    val sendMessageSuccess: ViewStateEmptyEvent?,
    val error: ViewStateEvent<String>?
): MviViewState<MessagesThreadResult> {

    companion object {
        fun default() = MessagesThreadViewState(false, null, null, null, null)
    }

    override fun isSavable(): Boolean = !inProgress

    override fun reduce(result: MessagesThreadResult): MessagesThreadViewState = when (result) {
        is InFlight -> result.reduce()
        is Error -> result.reduce()
        is MessageSendSuccess -> result.reduce()
        is Success -> result.reduce()
    }

    private fun InFlight.reduce() = this@MessagesThreadViewState.copy(
        inProgress = true,
        sendMessageSuccess = null,
        error = null
    )

    private fun Error.reduce() = this@MessagesThreadViewState.copy(
        inProgress = false,
        sendMessageSuccess = null,
        error = ViewStateEvent(errorMessage)
    )

    private fun MessageSendSuccess.reduce() = this@MessagesThreadViewState.copy(
        inProgress = false,
        sendMessageSuccess = ViewStateEmptyEvent(),
        error = null
    )

    private fun Success.reduce() = this@MessagesThreadViewState.copy(
        inProgress = false,
        messages = messages,
        uniqueMessages = uniqueMessages,
        sendMessageSuccess = null,
        error = null
    )
}