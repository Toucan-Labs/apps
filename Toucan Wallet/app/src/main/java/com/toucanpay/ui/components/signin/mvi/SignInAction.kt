package com.toucanpay.ui.components.signin.mvi

import com.toucanpay.ui.base.mvi.MviAction

sealed class SignInAction: MviAction {

    object LoadUserAction: SignInAction()

    object ResetPinAction: SignInAction()

    object SwitchUserAction: SignInAction()

    data class LoginUserAction(val password: String): SignInAction()
}