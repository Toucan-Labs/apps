package com.toucanwalletdemo.ui.components.signup.mvi

import com.toucanwalletdemo.ui.base.mvi.MviAction
import com.toucanwalletdemo.ui.model.UserRegistrationData

sealed class SignUpAction: MviAction {

    data class RegisterAction(val data: UserRegistrationData): SignUpAction()
}