package com.toucanwalletdemo.ui.components.newmessage

import com.toucanwalletdemo.ui.base.mvi.MviViewState
import com.toucanwalletdemo.ui.base.mvi.ViewStateEvent
import com.toucanwalletdemo.ui.components.newmessage.mvi.NewMessageResult
import com.toucanwalletdemo.ui.components.newmessage.mvi.NewMessageResult.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewMessageViewState(
    val inProgress: Boolean,
    val moveToThread: ViewStateEvent<String>?,
    val error: ViewStateEvent<String>?
): MviViewState<NewMessageResult> {

    companion object {
        fun default() = NewMessageViewState(false, null, null)
    }

    override fun isSavable(): Boolean = !inProgress

    override fun reduce(result: NewMessageResult): NewMessageViewState = when (result) {
        is InFlight -> result.reduce()
        is MoveToMessagesThread -> result.reduce()
        is Error -> result.reduce()
    }

    private fun InFlight.reduce() = this@NewMessageViewState.copy(
        inProgress = true,
        moveToThread = null,
        error = null
    )

    private fun MoveToMessagesThread.reduce() = this@NewMessageViewState.copy(
        inProgress = false,
        moveToThread = ViewStateEvent(username),
        error = null
    )

    private fun Error.reduce() = this@NewMessageViewState.copy(
        inProgress = false,
        moveToThread = null,
        error = ViewStateEvent(this.errorMessage)
    )
}