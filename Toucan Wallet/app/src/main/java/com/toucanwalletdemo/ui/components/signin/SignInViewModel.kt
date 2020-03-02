package com.toucanwalletdemo.ui.components.signin

import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.signin.mvi.SignInAction
import com.toucanwalletdemo.ui.components.signin.mvi.SignInActionProcessor
import com.toucanwalletdemo.ui.components.signin.mvi.SignInResult

class SignInViewModel: MviViewModel<SignInAction, SignInResult, SignInViewState>(
    SignInActionProcessor(),
    SignInViewState.default()
) {

    override fun initialAction(): SignInAction? {
        if (viewState.username == null) {
            return SignInAction.LoadUserAction
        }
        return null
    }

    fun onResetPin() = accept(SignInAction.ResetPinAction)

    fun onSwitchUser() = accept(SignInAction.SwitchUserAction)

    fun onLogin(password: String) = accept(SignInAction.LoginUserAction(password))
}