package com.toucanpay.ui.components.login.mvi

import com.toucanpay.data.models.LoginData
import com.toucanpay.ui.base.mvi.MviAction

sealed class LogInAction: MviAction {

    data class LoginUserAction(val credentials: LoginData): LogInAction()

    data class ResetPinAction(val identifier: String): LogInAction()
}