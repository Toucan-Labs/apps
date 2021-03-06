package com.toucanwalletdemo.ui.components.login

import com.toucanwalletdemo.data.models.LoginData
import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.login.mvi.LogInAction
import com.toucanwalletdemo.ui.components.login.mvi.LogInActionProcessor
import com.toucanwalletdemo.ui.components.login.mvi.LogInResult

class LogInViewModel: MviViewModel<LogInAction, LogInResult, LogInViewState>(
    LogInActionProcessor(),
    LogInViewState.default()
) {

    override fun initialAction(): Nothing? = null

    fun onLogin(identifier: String, password: String) =
        accept(LogInAction.LoginUserAction(LoginData(identifier, password)))

    fun onPINReset(identifier: String) =
        accept(LogInAction.ResetPinAction(identifier))
}