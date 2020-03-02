package com.toucanwalletdemo.ui.components.newmessage.mvi

import com.toucanwalletdemo.ui.base.mvi.MviAction

sealed class NewMessageAction: MviAction {

    data class MessageSendAction(val targetUsername: String): NewMessageAction()
}