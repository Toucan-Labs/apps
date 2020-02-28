package com.toucanpay.ui.components.verify.mvi

import com.toucanpay.ui.base.mvi.MviAction

sealed class VerifyAction: MviAction {

    data class VerifyUserAction(val code: String): VerifyAction()

    object SwitchUserAction: VerifyAction()
}