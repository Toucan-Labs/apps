package com.toucanwalletdemo.ui.components.signin.mvi

import com.toucanwalletdemo.ui.base.mvi.MviAction

sealed class SignInAction: MviAction {

    object LoadUserAction: SignInAction()

    object ResetPinAction: SignInAction()

    object SwitchUserAction: SignInAction()

    data class LoginUserAction(val password: String): SignInAction()
}