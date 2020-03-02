package com.toucanwalletdemo.ui.components.messages

import com.toucanwalletdemo.data.models.Message
import com.toucanwalletdemo.ui.base.mvi.MviViewState
import com.toucanwalletdemo.ui.base.mvi.ViewStateEvent
import com.toucanwalletdemo.ui.components.messages.mvi.MessagesResult
import com.toucanwalletdemo.ui.components.messages.mvi.MessagesResult.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessagesViewState(
    val inProgress: Boolean,
    val messages: List<Message>?,
    val error: ViewStateEvent<String>?
): MviViewState<MessagesResult> {

    companion object {
        fun default() = MessagesViewState(false, null, null)
    }

    override fun isSavable(): Boolean = !inProgress

    override fun reduce(result: MessagesResult): MessagesViewState = when (result) {
        is InFlight -> result.reduce()
        is Error -> result.reduce()
        is Success -> result.reduce()
    }

    private fun InFlight.reduce() = this@MessagesViewState.copy(
        inProgress = true,
        messages = null,
        error = null
    )

    private fun Error.reduce() = this@MessagesViewState.copy(
        inProgress = false,
        messages = null,
        error = ViewStateEvent(errorMessage)
    )

    private fun Success.reduce() = this@MessagesViewState.copy(
        inProgress = false,
        messages = messages,
        error = null
    )
}