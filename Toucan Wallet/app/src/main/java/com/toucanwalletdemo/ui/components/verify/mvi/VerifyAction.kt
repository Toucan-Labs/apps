package com.toucanwalletdemo.ui.components.verify.mvi

import com.toucanwalletdemo.ui.base.mvi.MviAction

sealed class VerifyAction: MviAction {

    data class VerifyUserAction(val code: String): VerifyAction()

    object SwitchUserAction: VerifyAction()
}