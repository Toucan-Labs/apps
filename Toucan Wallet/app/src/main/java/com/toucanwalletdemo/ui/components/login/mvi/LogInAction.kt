package com.toucanwalletdemo.ui.components.login.mvi

import com.toucanwalletdemo.data.models.LoginData
import com.toucanwalletdemo.ui.base.mvi.MviAction

sealed class LogInAction: MviAction {

    data class LoginUserAction(val credentials: LoginData): LogInAction()

    data class ResetPinAction(val identifier: String): LogInAction()
}