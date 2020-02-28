package com.toucanpay.ui.components.newmessage.mvi

import com.toucanpay.ui.base.mvi.MviAction

sealed class NewMessageAction: MviAction {

    data class MessageSendAction(val targetUsername: String): NewMessageAction()
}